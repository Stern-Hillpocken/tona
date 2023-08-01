package fr.tona.util;

import org.springframework.stereotype.Component;

@Component
public class DieInteraction {
    public Integer roll(String xdx){
        Integer times = Integer.parseInt(xdx.split("d",0)[0]);
        Integer dieType = Integer.parseInt(xdx.split("d",0)[1]);
        Integer totalOfRolls = 0;
        for(int i = 0; i < times; i++){
            totalOfRolls += 1 + (int)(Math.random() * dieType);
        }
        return totalOfRolls;
    }
}
