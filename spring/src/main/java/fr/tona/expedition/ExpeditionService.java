package fr.tona.expedition;

import fr.tona.chat_message.ChatMessage;
import fr.tona.majagaba.Majagaba;
import fr.tona.pod.Pod;
import fr.tona.pod.PodRepository;
import fr.tona.pod_register.PodRegister;
import fr.tona.room.Room;
import fr.tona.user.User;
import fr.tona.user.UserRepository;
import fr.tona.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExpeditionService {

    private final ExpeditionRepository repository;

    private final JwtService jwtService;

    public void launch(PodRegister podRegister, User captain){

        Expedition expedition = new Expedition();
        // TODO L'expédition crée et bind les Majagaban
        for(int nb = 0; nb < podRegister.getCharacterMax(); nb ++){
            User user = jwtService.grepUserFromJwt();
            Majagaba majagaba = new Majagaba();
            majagaba.setJob("all");
            majagaba.setDicePool(new ArrayList<Integer>(){{
                add(1 + (int)(Math.random() * (6 - 1)));
                add(1 + (int)(Math.random() * (6 - 1)));
                add(1 + (int)(Math.random() * (6 - 1)));
                add(1 + (int)(Math.random() * (6 - 1)));
            }});
            majagaba.setRoom("ICI");

            user.setMajagaba(majagaba);

            expedition.getCrew().add(user);
        }

        expedition.setName(podRegister.getName());
        expedition.setDifficulty(podRegister.getDifficulty());

        Pod pod = new Pod();
        Room bathroom = new Room();
        bathroom.setName("BathRoom");
        pod.getRooms().add(bathroom);

        expedition.setPod(pod);
        expedition.setCaptain(captain);
        expedition.getCrew().add(captain);

        repository.save(expedition);
    }

    public Expedition getMy() {
        User user = jwtService.grepUserFromJwt();
        repository.getById(user.getExpedition().getId());
        return repository.getById(user.getExpedition().getId());
    }

    public Expedition endTurn() {
        User user = jwtService.grepUserFromJwt();
        Expedition expeditionFound = repository.getById(user.getExpedition().getId());
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

    public List<ChatMessage> getAllChatMessages() {
        User user = jwtService.grepUserFromJwt();
        return repository.findById(user.getExpedition().getId()).get().getMessages();
    }

    public void sendMessage(String messageContents) {
        while(messageContents.length() > 0 && (messageContents.charAt(0) == ' ' || messageContents.charAt(0) == '\n')){
            if(messageContents.charAt(0) == ' '){
                messageContents = messageContents.substring(1);
            }else if(messageContents.charAt(0) == '\n'){
                messageContents = "";
            }
        }
        if(messageContents.length() > 0){
            User user = jwtService.grepUserFromJwt();

            ChatMessage fullMessage = new ChatMessage();
            fullMessage.setUser(user);

            String pattern = "yyyy-MM-dd'T'HH:mm:ss";
            DateFormat df = new SimpleDateFormat(pattern);
            Date today = Calendar.getInstance().getTime();
            String now = df.format(today);
            fullMessage.setDate(now);

            fullMessage.setContents(messageContents);

            Expedition currentExpedition = repository.findById(user.getExpedition().getId()).orElseThrow(
                    () -> new RuntimeException("Id of expedition not found")
            );
            currentExpedition.getMessages().add(fullMessage);
            repository.save(currentExpedition);
        }
    }
}
