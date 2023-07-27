package fr.tona.majagaba;

import fr.tona.expedition.Expedition;
import fr.tona.expedition.ExpeditionService;
import fr.tona.room.Room;
import fr.tona.user.User;
import fr.tona.util.DieAction;
import fr.tona.util.DieAllowedCheck;
import fr.tona.util.JwtService;
import fr.tona.workshop.Workshop;
import fr.tona.workshop.WorkshopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MajagabaService {

    private final MajagabaRepository repository;

    private final JwtService jwtService;

    private final WorkshopRepository workshopRepository;

    private final DieAllowedCheck dieAllowedCheck;


    public void reroll() {
        User user = jwtService.grepUserFromJwt();
        Majagaba majagaba = user.getMajagaba();
        if(majagaba.getRerollLeft() > 0){
            int numberOfDice = majagaba.getDicePool().size();
            majagaba.setDicePool(new ArrayList<>());
            for(int i = 0; i < numberOfDice; i++){
                majagaba.getDicePool().add(1 + (int)(Math.random() * (6 - 1)));
            }
            majagaba.setRerollLeft(majagaba.getRerollLeft()-1);
            repository.save(majagaba);
        }
    }

    public void stockDie(Integer value) {
        User user = jwtService.grepUserFromJwt();
        Majagaba majagaba = user.getMajagaba();
        for(int i = 0; i < majagaba.getDicePool().size(); i++){
            if(majagaba.getDicePool().get(i).equals(value)){
                majagaba.getDiceStocked().add(value);
                majagaba.getDicePool().remove(value);
                break;
            }
        }
        repository.save(majagaba);
    }

    public void destockDie(Integer value){
        User user = jwtService.grepUserFromJwt();
        Majagaba majagaba = user.getMajagaba();
        for(int i = 0; i < majagaba.getDiceStocked().size(); i++){
            if(majagaba.getDiceStocked().get(i).equals(value)){
                majagaba.getDicePool().add(value);
                majagaba.getDiceStocked().remove(value);
                break;
            }
        }
        repository.save(majagaba);
    }

    public void endTurn(Majagaba majagaba){
        // Reroll left
        majagaba.setRerollLeft(2);
        // Reroll dice
        if(majagaba.getDiceStocked().size() > 0){
            Integer lockedDie = majagaba.getDiceStocked().get(0);
            majagaba.setDiceStocked(new ArrayList<>());
            majagaba.getDiceStocked().add(lockedDie);
        }else{
            majagaba.setDiceStocked(new ArrayList<>());
        }
        majagaba.setDicePool(new ArrayList<>());
        for(int i = 0; i < 4; i++){
            majagaba.getDicePool().add(1 + (int)(Math.random() * 6));
        }
        repository.save(majagaba);
    }

    public void addBlastedDice(Integer times){
        Expedition expedition = jwtService.grepUserFromJwt().getExpedition();
        Integer crewIndex = 0;
        while(times > 0){
            Majagaba majagaba = expedition.getCrew().get(crewIndex).getMajagaba();
            majagaba.getDicePool().add(1 + (int)(Math.random() * (6 - 1)));
            repository.save(majagaba);
            crewIndex ++;
            if(crewIndex >= expedition.getCrew().size()) crewIndex = 0;
            times --;
        }
    }

    public void move(DieAction action){
        if(action.getEndZone().equals("armory") || action.getEndZone().equals("drill") || action.getEndZone().equals("extractor") || action.getEndZone().equals("hoist") || action.getEndZone().equals("hold") || action.getEndZone().equals("porthole")){
            User user = jwtService.grepUserFromJwt();
            Majagaba majagaba = user.getMajagaba();
            if(!majagaba.getRoom().equals(action.getEndZone()) && this.isZoneAdjacent(action.getEndZone(), majagaba.getRoom())){

                if(action.getStartZone().equals("dice-pool-zone")){
                    for(int i = 0; i < majagaba.getDicePool().size(); i++){
                        if(majagaba.getDicePool().get(i).equals(action.getDieValue())){
                            majagaba.getDicePool().remove(i);
                            majagaba.setRoom(action.getEndZone());
                            repository.save(majagaba);
                            break;
                        }
                    }
                }else if(action.getStartZone().equals("dice-stocked-zone")){
                    for(int i = 0; i < majagaba.getDiceStocked().size(); i++){
                        if(majagaba.getDiceStocked().get(i).equals(action.getDieValue())){
                            majagaba.getDiceStocked().remove(i);
                            majagaba.setRoom(action.getEndZone());
                            repository.save(majagaba);
                            break;
                        }
                    }
                }

            }
        }
    }

    private Boolean isZoneAdjacent(String selectedRoom, String currentRoom){
        if(currentRoom.equals("hoist") && (selectedRoom.equals("hold") || selectedRoom.equals("extractor"))){
            return true;
        }else if(currentRoom.equals("hold") && (selectedRoom.equals("hoist") || selectedRoom.equals("extractor") || selectedRoom.equals("armory"))){
            return true;
        }else if(currentRoom.equals("extractor") && (selectedRoom.equals("porthole") || selectedRoom.equals("hold") || selectedRoom.equals("hoist"))){
            return true;
        }else if(currentRoom.equals("armory") && (selectedRoom.equals("hold") || selectedRoom.equals("porthole") || selectedRoom.equals("drill"))){
            return true;
        }else if(currentRoom.equals("porthole") && (selectedRoom.equals("drill") || selectedRoom.equals("armory") || selectedRoom.equals("extractor"))){
            return true;
        }else if(currentRoom.equals("drill") && (selectedRoom.equals("armory") || selectedRoom.equals("porthole"))){
            return true;
        }else{
            return false;
        }
    }

    public void allocate(DieAction action) {
        //if(!isEndZoneExist(action.getEndZone())) return;
        User user = jwtService.grepUserFromJwt();
        Majagaba majagaba = user.getMajagaba();
        if(!isDieExist(majagaba, action)) return;

        if(action.getEndZone().contains("one-pip") && majagaba.getSteamRegulator() > 0){
            if((action.getEndZone().equals("remove-one-pip") && action.getDieValue().equals(1)) || (action.getEndZone().equals("add-one-pip") && action.getDieValue().equals(6))) return;
            List<Integer> diceList = action.getEndZone().equals("dice-stocked-zone") ? majagaba.getDiceStocked() : majagaba.getDicePool();
            Integer index = diceList.indexOf(action.getDieValue());
            if(action.getEndZone().equals("remove-one-pip")){
                diceList.set(index, diceList.get(index)-1);
            }else{
                diceList.set(index, diceList.get(index)+1);
            }
            majagaba.setSteamRegulator(majagaba.getSteamRegulator()-1);
            repository.save(majagaba);

        }else if(action.getEndZone().startsWith("hold-") && majagaba.getRoom().equals("hold")){
            if(action.getEndZone().startsWith("hold-craft-")){
                Workshop workshop = getAskedWorkshop(user, action.getEndZone());
                Integer zoneIndex = Integer.parseInt(action.getEndZone().substring(action.getEndZone().length()-1));

                if(!workshop.getStoredDice()[zoneIndex].equals(0)) return;

                if(action.getEndZone().startsWith("hold-craft-steam-blast") && !dieAllowedCheck.isSame(workshop.getStoredDice(), action.getDieValue())) return;
                if(action.getEndZone().startsWith("hold-craft-steam-switcher") && !dieAllowedCheck.isDifferent(workshop.getStoredDice(), action.getDieValue())) return;
                if(action.getEndZone().startsWith("hold-craft-steam-regulator") && !dieAllowedCheck.isSequence(workshop.getStoredDice(), action.getDieValue())) return;

                workshop.getStoredDice()[zoneIndex] = action.getDieValue();
                workshopRepository.save(workshop);

                List<Integer> diceList = action.getStartZone().equals("dice-stocked-zone") ? majagaba.getDiceStocked() : majagaba.getDicePool();
                Integer index = diceList.indexOf(action.getDieValue());
                diceList.remove((int)index);// (int) and not the value
                repository.save(majagaba);
            }
        }
    }

    public void takeObject(String objectName){
        if(!(objectName.equals("steam-blast") || objectName.equals("steam-switcher") || objectName.equals("steam-regulator"))) return;
        User user = jwtService.grepUserFromJwt();
        Expedition expedition = user.getExpedition();
        Majagaba majagaba = user.getMajagaba();
        if(!majagaba.getRoom().equals("hold")) return;

        Workshop workshop = getAskedWorkshop(user, "hold-craft-"+objectName);
        Integer[] diceList = workshop.getStoredDice();
        int zeroValueCount = 0;
        for(int i = 0; i < diceList.length; i++){
            if(diceList[i] == 0) zeroValueCount ++;
        }
        if(zeroValueCount == 0){
            if(objectName.equals("steam-blast")) majagaba.setSteamBlast(majagaba.getSteamBlast()+1);
            if(objectName.equals("steam-switcher")) majagaba.setSteamSwitcher(majagaba.getSteamSwitcher()+1);
            if(objectName.equals("steam-regulator")) majagaba.setSteamRegulator(majagaba.getSteamRegulator()+1);
            repository.save(majagaba);
            for(int i = 0; i < diceList.length; i++){
                diceList[i] = 0;
            }
            workshopRepository.save(workshop);
        }
    }

    public void useSteamBlast(){
        Majagaba majagaba = jwtService.grepUserFromJwt().getMajagaba();
        majagaba.setSteamBlast(majagaba.getSteamBlast()-1);
        repository.save(majagaba);
    }

    private Workshop getAskedWorkshop(User user, String askedName){
        String roomName = askedName.split("-")[0];
        String workshopName = askedName.split(" ")[0];
        Integer indexOfRoom = -1;
        Integer indexOfWorkshop = -1;
        for(int i = 0; i < user.getExpedition().getPod().getRooms().size(); i++){
            if(user.getExpedition().getPod().getRooms().get(i).getName().equals(roomName)){
                indexOfRoom = i;
                for(int j = 0; j < user.getExpedition().getPod().getRooms().get(i).getWorkshops().size(); j++){
                    if(user.getExpedition().getPod().getRooms().get(i).getWorkshops().get(j).getName().equals(workshopName)) indexOfWorkshop = j;
                }
            }
        }
        return user.getExpedition().getPod().getRooms().get(indexOfRoom).getWorkshops().get(indexOfWorkshop);
    }

    private Boolean isDieExist(Majagaba majagaba, DieAction action){
        String startZone = action.getStartZone();
        if(startZone.equals("dice-stocked-zone") || startZone.equals("dice-pool-zone")){
            List<Integer> diceList = startZone.equals("dice-stocked-zone") ? majagaba.getDiceStocked() : majagaba.getDicePool();
            for(int i = 0; i < diceList.size(); i++){
                if(diceList.get(i).equals(action.getDieValue())) return true;
            }
        }
        return false;
    }

    /*private Boolean isEndZoneExist(String name){
        return switch (name) {
            case "remove-one-pip", "add-one-pip", "hold-craft 1" -> true;
            default -> false;
        };
    }*/


    /*private Integer indexOfDie(Integer valueSearched, List<Integer> diceList){
        for(int i = 0; i < diceList.size(); i++){
            if(diceList.get(i).equals(valueSearched)) return i;
        }
        return -1;
    }*/
}
