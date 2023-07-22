package fr.tona.majagaba;

import fr.tona.user.User;
import fr.tona.util.DieAction;
import fr.tona.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MajagabaService {

    private final MajagabaRepository repository;

    private final JwtService jwtService;

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
        majagaba.setDiceStocked(new ArrayList<>());
        majagaba.setDicePool(new ArrayList<>());
        for(int i = 0; i < 4; i++){
            majagaba.getDicePool().add(1 + (int)(Math.random() * (6 - 1)));
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
        if(!isEndZoneExist(action.getEndZone())) return;
        User user = jwtService.grepUserFromJwt();
        Majagaba majagaba = user.getMajagaba();
        if(!isDieExist(majagaba, action)) return;

        if(action.getEndZone().contains("one-pip") && majagaba.getSteamRegulator() > 0){
            if((action.getEndZone().equals("remove-one-pip") && action.getDieValue().equals(1)) || (action.getEndZone().equals("add-one-pip") && action.getDieValue().equals(6))) return;
            List<Integer> diceList = action.getEndZone().equals("dice-stocked-zone") ? majagaba.getDiceStocked() : majagaba.getDicePool();
            Integer index = indexOfDie(action.getDieValue(), diceList);
            if(action.getEndZone().equals("remove-one-pip")){
                diceList.set(index, diceList.get(index)-1);
            }else{
                diceList.set(index, diceList.get(index)+1);
            }
            majagaba.setSteamRegulator(majagaba.getSteamRegulator()-1);
            repository.save(majagaba);
        }
    }

    private Boolean isEndZoneExist(String name){
        return switch (name) {
            case "remove-one-pip", "add-one-pip" -> true;
            default -> false;
        };
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

    private Integer indexOfDie(Integer valueSearched, List<Integer> diceList){
        for(int i = 0; i < diceList.size(); i++){
            if(diceList.get(i).equals(valueSearched)) return i;
        }
        return -1;
    }
}
