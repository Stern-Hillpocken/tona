package fr.tona.majagaba;

import fr.tona.expedition.Expedition;
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
import java.util.Arrays;
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
            if(action.getEndZone().startsWith("hold-craft-steam-regulator")){
                Workshop workshop = user.getExpedition().getPod().getRooms().get(indexOfRoomName(
                        user.getExpedition().getPod().getRooms(),"hold")
                ).getWorkshops().get(2);
                Integer zoneIndex = Integer.parseInt(action.getEndZone().substring(action.getEndZone().length()-1));

                if(!workshop.getStoredDice()[zoneIndex].equals(0)) return;
                if(!dieAllowedCheck.isSequence(workshop.getStoredDice(), action.getDieValue())) return;

                workshop.getStoredDice()[zoneIndex] = action.getDieValue();
                workshopRepository.save(workshop);

                List<Integer> diceList = action.getEndZone().equals("dice-stocked-zone") ? majagaba.getDiceStocked() : majagaba.getDicePool();
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

        if(objectName.equals("steam-regulator")){
            Workshop workshop = expedition.getPod().getRooms().get(indexOfRoomName(expedition.getPod().getRooms(), "hold")).getWorkshops().get(2);
            Integer[] diceList = workshop.getStoredDice();
            int zeroCount = 0;
            for(int i = 0; i < diceList.length; i++){
                if(diceList[i] == 0) zeroCount ++;
            }
            if(zeroCount == 0){
                majagaba.setSteamRegulator(majagaba.getSteamRegulator()+1);
                repository.save(majagaba);
                for(int i = 0; i < diceList.length; i++){
                    diceList[i] = 0;
                }
                workshopRepository.save(workshop);
            }
        }
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

    private Integer indexOfRoomName(List<Room> roomList, String name){
        for(int i = 0; i < roomList.size(); i++){
            if(roomList.get(i).getName().equals(name)) return i;
        }
        return -1;
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
