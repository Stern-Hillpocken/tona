package fr.tona.util;

import fr.tona.majagaba.Majagaba;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DieAllowedCheck {

    public Boolean isSequence(Integer[] storedDice, Integer die){
        Integer countZero = 0;
        Integer min = 7;
        Integer max = 0;
        for(int i = 0; i < storedDice.length; i++){
            if(storedDice[i].equals(die)) return false;
            else if(storedDice[i].equals(0)){
                countZero++;
            }else{
                min = Math.min(min, storedDice[i]);
                max = Math.max(max, storedDice[i]);
            }
        }

        if(countZero.equals(storedDice.length)) return true;
        // work for 3 spaces
        if(max-min == 2 && (die == min+1 || die == max-1)) return true;
        if(max-min == 1 && (die == min-1 || die == max+1)) return true;
        if(max-min == 0 && (die >= min-countZero && die <= max+countZero)) return true;
        return false;
    }


    public Boolean isSame(Integer[] storedDice, Integer die){
        Integer countZero = 0;
        Integer valueAllowed = 0;
        for(int i = 0; i < storedDice.length; i++){
            if(storedDice[i] == 0) countZero++;
            else valueAllowed = storedDice[i];
        }
        if(countZero == storedDice.length) return true;
        if(die.equals(valueAllowed)) return true;
        return false;
    }

    private Integer sum(Integer[] storedDice){
        Integer total = 0;
        for(int i = 0; i < storedDice.length; i++){
            total += storedDice[i];
        }
        return total;
    }

    public Boolean isAbleToReachSum(Integer[] storedDice, Integer die, Integer totalToReach){
        Integer countZero = 0;
        Integer currentSum = 0;
        for(int i = 0; i < storedDice.length; i++){
            if(storedDice[i] == 0) countZero++;
            else currentSum += storedDice[i];
        }
        if((currentSum+die) + 6*(countZero-1) >= totalToReach) return true;
        return false;
    }

    public Boolean isDifferent(Integer[] storedDice, Integer die){
        for(int i = 0; i < storedDice.length; i++){
            if(storedDice[i].equals(die)) return false;
        }
        return true;
    }

    public Boolean isEven(Integer die){
        if(die == 2 || die == 4 || die == 6) return true;
        return false;
    }
}
