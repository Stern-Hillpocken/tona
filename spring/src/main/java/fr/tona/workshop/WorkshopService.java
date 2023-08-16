package fr.tona.workshop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkshopService {

    private final WorkshopRepository repository;

    public Boolean isFull(Workshop workshop){
        for(int i = 0; i < workshop.getStoredDice().length; i++){
            if(workshop.getStoredDice()[i] == 0) return false;
        }
        return true;
    }

    public void emptyThis(Workshop workshop){
        for(int i = 0; i < workshop.getStoredDice().length; i++){
            workshop.getStoredDice()[i] = 0;
        }
        repository.save(workshop);
    }

    public boolean nameIsCorrect(String room) {
        if(room.equals("hoist") || room.equals("hold") || room.equals("extractor") || room.equals("armory") || room.equals("porthole") || room.equals("drill")) return true;
        return false;
    }
}
