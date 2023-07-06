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
        expedition.setCrew(new HashSet<Majagaba>());
        // TODO L'expédition crée et bind les Majagaban
        for(int nb = 0; nb < podRegister.getCharacterMax(); nb ++){
            Majagaba majagaba = new Majagaba();
            majagaba.setUser(captain);
            majagaba.setLife(5);
            majagaba.setJob("all");
            majagaba.setDicePool(new ArrayList<Integer>(){{
                add(1 + (int)(Math.random() * (6 - 1)));
                add(1 + (int)(Math.random() * (6 - 1)));
                add(1 + (int)(Math.random() * (6 - 1)));
                add(1 + (int)(Math.random() * (6 - 1)));
            }});
            majagaba.setDiceStocked(new ArrayList<>());
            majagaba.setRerollLeft(2);
            majagaba.setRoom("ICI");

            expedition.getCrew().add(majagaba);
        }
        expedition.setName(podRegister.getName());
        expedition.setDifficulty(podRegister.getDifficulty());
        expedition.setDay(0L);
        expedition.setHour(0);
        expedition.setMinute(0);

        Pod pod = new Pod();
        pod.setHealth(10);
        pod.setRooms(new HashSet<>());
        Room bathroom = new Room();
        bathroom.setName("BathRoom");
        pod.getRooms().add(bathroom);
        expedition.setPod(pod);

        expedition.setCaptain(captain);
        expedition.setWater(0L);
        expedition.setMessages(new ArrayList<>());
        expedition.setDepth(0L);
        expedition.setStatus("ingame");

        repository.save(expedition);
    }

    public Expedition getMy() {
        User user = jwtService.grepUserFromJwt();
        return repository.getById(user.getExpedition().getId());
    }

    public Expedition reroll() {
        User user = jwtService.grepUserFromJwt();
        Expedition expedition = repository.getById(user.getExpedition().getId());
        expedition.setDepth(expedition.getDepth()+1L);// TODO
        //expedition.getCrew();
        repository.save(expedition);
        return expedition;
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
