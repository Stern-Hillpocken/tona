package fr.tona.majagaba;

import fr.tona.user.User;
import fr.tona.util.DieAction;
import fr.tona.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if(action.getEndZone().equals("armory") || action.getEndZone().equals("drill") || action.getEndZone().equals("extractor") || action.getEndZone().equals("hoist") || action.getEndZone().equals("hold") || action.getEndZone().equals("???")){
            User user = jwtService.grepUserFromJwt();
            Majagaba majagaba = user.getMajagaba();
            if(!majagaba.getRoom().equals(action.getEndZone())){

                if(action.getStartZone().equals("dice-pool-zone")){
                    for(int i = 0; i < majagaba.getDicePool().size(); i++){
                        System.out.println(action.getDieValue()+"->"+majagaba.getDicePool().get(i));
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

}
