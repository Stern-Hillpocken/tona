package fr.tona.expedition;

import fr.tona.chat_message.ChatMessage;
import fr.tona.majagaba.Majagaba;
import fr.tona.majagaba.MajagabaService;
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

    private final MajagabaService majagabaService;

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
            majagaba.setRoom("hold");

            user.setMajagaba(majagaba);

            expedition.getCrew().add(user);
        }

        expedition.setName(podRegister.getName());

        expedition.setDifficulty(podRegister.getDifficulty());

        Pod pod = new Pod();
        // Hoist
        Room hoist = new Room();
        hoist.setName("hoist");
        pod.getRooms().add(hoist);
        // Hold
        Room hold = new Room();
        hold.setName("hold");
        pod.getRooms().add(hold);
        // Extractor
        Room extractor = new Room();
        extractor.setName("extractor");
        pod.getRooms().add(extractor);
        // Armory
        Room armory = new Room();
        armory.setName("armory");
        pod.getRooms().add(armory);
        // Porthole
        Room porthole = new Room();
        porthole.setName("porthole");
        pod.getRooms().add(porthole);
        // Drill
        Room drill = new Room();
        drill.setName("drill");
        pod.getRooms().add(drill);
        //
        if(expedition.getDifficulty() == 2){
            pod.setHealth(8);
            pod.setMaxHealth(8);
        }

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
        Expedition expedition = repository.getById(user.getExpedition().getId());
        // Time
        Integer addingMinute = 15;
        expedition.setMinute(expedition.getMinute()+addingMinute);
        if(expedition.getMinute() >= 60){
            expedition.setHour(expedition.getHour()+(int)(expedition.getMinute() / 60));
            expedition.setMinute(expedition.getMinute()%60);
        }
        if(expedition.getHour() >= 24){
            expedition.setDay(expedition.getDay()+(int)(expedition.getHour() / 24));
            expedition.setHour(expedition.getHour()%24);
        }

        // Majagaba
        List<User> userList = new ArrayList<User>(expedition.getCrew());
        for(int c = 0; c < expedition.getCrew().size(); c++){
            majagabaService.endTurn(userList.get(c).getMajagaba());
        }

        repository.save(expedition);
        return expedition;
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
