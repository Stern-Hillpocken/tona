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

    public void endTurn(Expedition expedition, Majagaba majagaba){
        String currentRoomStatus = expedition.getPod().getRooms().get((int)roomId(majagaba.getRoom())).getStatus();
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
        int diceNumber = 4;
        if(currentRoomStatus.equals("fan")) diceNumber --;
        for(int i = 0; i < diceNumber; i++){
            majagaba.getDicePool().add(1 + (int)(Math.random() * 6));
        }
        // Fire
        if(currentRoomStatus.equals("fire")) majagaba = takeDamage(majagaba, 1);
        // Powercharge
        if(majagaba.getPowerCharge() < majagaba.getPowerChargeMax()) majagaba.setPowerCharge(majagaba.getPowerCharge()+1);

        repository.save(majagaba);
    }

    public void addBlastedDice(Integer times){
        Expedition expedition = jwtService.grepUserFromJwt().getExpedition();
        int crewIndex = 0;
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
        if(!action.getEndZone().equals("armory") && !action.getEndZone().equals("drill") && !action.getEndZone().equals("extractor") && !action.getEndZone().equals("hoist") && !action.getEndZone().equals("hold") && !action.getEndZone().equals("porthole")) return;
        User user = jwtService.grepUserFromJwt();
        Majagaba majagaba = user.getMajagaba();
        Expedition expedition = user.getExpedition();
        boolean isRoomWithGlue = expedition.getPod().getRooms().get(roomId(user.getMajagaba().getRoom())).getStatus().equals("glue");
        if(isRoomWithGlue) return;

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
            int index = diceList.indexOf(action.getDieValue());
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
                int zoneIndex = Integer.parseInt(action.getEndZone().substring(action.getEndZone().length()-1));
                if(!workshop.getStoredDice()[zoneIndex].equals(0)) return;

                if(action.getEndZone().startsWith("hold-craft-steam-blast") && !dieAllowedCheck.isSame(workshop.getStoredDice(), action.getDieValue())) return;
                if(action.getEndZone().startsWith("hold-craft-steam-switcher") && !dieAllowedCheck.isDifferent(workshop.getStoredDice(), action.getDieValue())) return;
                if(action.getEndZone().startsWith("hold-craft-steam-regulator") && !dieAllowedCheck.isSequence(workshop.getStoredDice(), action.getDieValue())) return;

                workshop.getStoredDice()[zoneIndex] = action.getDieValue();
                workshopRepository.save(workshop);

                /*List<Integer> diceList = action.getStartZone().equals("dice-stocked-zone") ? majagaba.getDiceStocked() : majagaba.getDicePool();
                Integer index = diceList.indexOf(action.getDieValue());
                diceList.remove((int)index);// (int) and not the value
                repository.save(majagaba);*/
                useDie(majagaba, action);
            }
        }else if(action.getEndZone().startsWith("extractor-probe") && majagaba.getRoom().equals("extractor")){
            Workshop workshop = getAskedWorkshop(user, action.getEndZone());
            int zoneIndex = Integer.parseInt(action.getEndZone().substring(action.getEndZone().length()-1));
            if(!workshop.getStoredDice()[zoneIndex].equals(0)) return;

            if(!dieAllowedCheck.isSequence(workshop.getStoredDice(), action.getDieValue())) return;

            workshop.getStoredDice()[zoneIndex] = action.getDieValue();
            workshopRepository.save(workshop);
            useDie(majagaba, action);
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
        int indexOfRoom = -1;
        int indexOfWorkshop = -1;
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

    public Boolean isDieExist(Majagaba majagaba, DieAction action){
        String startZone = action.getStartZone();
        if(startZone.equals("dice-stocked-zone") || startZone.equals("dice-pool-zone")){
            List<Integer> diceList = startZone.equals("dice-stocked-zone") ? majagaba.getDiceStocked() : majagaba.getDicePool();
            for(int i = 0; i < diceList.size(); i++){
                if(diceList.get(i).equals(action.getDieValue())) return true;
            }
        }
        return false;
    }

    public void useDie(Majagaba majagaba, DieAction action){
        String startZone = action.getStartZone();
        List<Integer> diceList = startZone.equals("dice-stocked-zone") ? majagaba.getDiceStocked() : majagaba.getDicePool();
        for(int i = 0; i < diceList.size(); i++){
            if(diceList.get(i).equals(action.getDieValue())){
                diceList.remove((int) i);
                break;
            }
        }
        repository.save(majagaba);
    }

    public void reload(DieAction action){
        Majagaba majagaba = jwtService.grepUserFromJwt().getMajagaba();
        Workshop workshop = jwtService.grepUserFromJwt().getExpedition().getPod().getRooms().get(3).getWorkshops().get(1);
        int zone = Integer.parseInt(action.getEndZone().substring(action.getEndZone().length()-1));
        workshop.getStoredDice()[zone] = action.getDieValue();
        workshopRepository.save(workshop);
        useDie(majagaba, action);
    }

    public void useSpice(Majagaba majagaba) {
        majagaba.setLife(majagaba.getLife()+2);
        if(majagaba.getLife() > majagaba.getMaxLife()) majagaba.setLife(majagaba.getMaxLife());
        repository.save(majagaba);
    }

    private Integer roomId(String roomName) {
        return switch (roomName) {
            case "hoist" -> 0;
            case "hold" -> 1;
            case "extractor" -> 2;
            case "armory" -> 3;
            case "porthole" -> 4;
            case "drill" -> 5;
            default -> -1;
        };
    }

    private Majagaba takeDamage(Majagaba majagaba, Integer value) {
        majagaba.setLife(majagaba.getLife()-value);
        return majagaba;
    }

    public void jobActivation(Integer dieValue) {
        Majagaba majagaba = jwtService.grepUserFromJwt().getMajagaba();
        if (majagaba.getPowerCharge() < majagaba.getPowerChargeMax()) return;
        if (majagaba.getJob().equals("gunner") && dieValue == 1) return;
        if (majagaba.getJob().equals("miner") && dieValue == 6) return;
        boolean isDieValueExist = false;
        for (int i = 0; i < majagaba.getDicePool().size(); i++){
            if (majagaba.getDicePool().get(i).equals(dieValue)) { isDieValueExist = true; break;}
        }
        if ((majagaba.getJob().equals("gunner") || majagaba.getJob().equals("miner") || majagaba.getJob().equals("leader")) && !isDieValueExist) return;

        if (majagaba.getJob().equals("gunner")){
            for (int i = 0; i < majagaba.getDicePool().size(); i++) {
                if (majagaba.getDicePool().get(i).equals(dieValue)) { majagaba.getDicePool().set(i, majagaba.getDicePool().get(i)-1); break;}
            }
        } else if (majagaba.getJob().equals("miner")) {
            for (int i = 0; i < majagaba.getDicePool().size(); i++) {
                if (majagaba.getDicePool().get(i).equals(dieValue)) { majagaba.getDicePool().set(i, majagaba.getDicePool().get(i)+1); break;}
            }
        }

        majagaba.setPowerCharge(0);
        repository.save(majagaba);
    }
}
