package fr.tona.expedition;

import fr.tona.podregister.PodRegister;
import fr.tona.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExpeditionService {

    private final ExpeditionRepository repository;

    public void launch(PodRegister podRegister, User captain){
        Expedition expedition = new Expedition();
        expedition.setName(podRegister.getName());
        expedition.setDifficulty(podRegister.getDifficulty());
        expedition.setDay(0L);
        expedition.setHour(0);
        expedition.setMinute(0);
        expedition.setCaptain(captain);
        expedition.setWater(0L);
        expedition.setStatus("ingame");
        repository.save(expedition);
    }

    public Expedition getMy() {
        return repository.getById(1L);// TODO
    }

    public Expedition endTurn() {
        Expedition expeditionFound = repository.getById(1L);// TODO
        // Time
        Integer addingMinute = 15;
        expeditionFound.setMinute(expeditionFound.getMinute()+addingMinute);
        if(expeditionFound.getMinute() >= 60){
            expeditionFound.setHour(expeditionFound.getHour()+(int)(expeditionFound.getMinute() / 60));
            expeditionFound.setMinute(expeditionFound.getMinute()%60);
        }
        if(expeditionFound.getHour() >= 24){
            expeditionFound.setDay(expeditionFound.getDay()+(int)(expeditionFound.getHour() / 24));
            expeditionFound.setHour(expeditionFound.getHour()%24);
        }

        repository.save(expeditionFound);
        return expeditionFound;
    }
}
