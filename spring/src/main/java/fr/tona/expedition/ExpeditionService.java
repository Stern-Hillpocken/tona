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
import fr.tona.util.Workshop;
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
        Workshop hoistPW = new Workshop();
        hoistPW.setName("go back up");
        hoist.getWorkshops().add(hoistPW);
        Workshop hoistSW = new Workshop();
        hoistSW.setName("go back up 2");
        hoist.getWorkshops().add(hoistSW);
        pod.getRooms().add(hoist);

        // Hold
        Room hold = new Room();
        hold.setName("hold");
        Workshop holdPW = new Workshop();
        holdPW.setName("hold up");
        hold.getWorkshops().add(holdPW);
        Workshop holdSW = new Workshop();
        holdSW.setName("hold up 2");
        hold.getWorkshops().add(holdSW);
        pod.getRooms().add(hold);

        // Extractor
        Room extractor = new Room();
        extractor.setName("extractor");
        Workshop extractorPW = new Workshop();
        extractorPW.setName("extractor up");
        extractor.getWorkshops().add(extractorPW);
        Workshop extractorSW = new Workshop();
        extractorSW.setName("extractor up 2");
        extractor.getWorkshops().add(extractorSW);
        pod.getRooms().add(extractor);

        // Armory
        Room armory = new Room();
        armory.setName("armory");
        Workshop armoryPW = new Workshop();
        armoryPW.setName("armory up");
        armory.getWorkshops().add(armoryPW);
        Workshop armorySW = new Workshop();
        armorySW.setName("armory up 2");
        armory.getWorkshops().add(armorySW);
        pod.getRooms().add(armory);

        // Porthole
        Room porthole = new Room();
        porthole.setName("porthole");
        Workshop portholePW = new Workshop();
        portholePW.setName("porthole up");
        porthole.getWorkshops().add(portholePW);
        Workshop portholeSW = new Workshop();
        portholeSW.setName("porthole up 2");
        porthole.getWorkshops().add(portholeSW);
        pod.getRooms().add(porthole);

        // Drill
        Room drill = new Room();
        drill.setName("drill");
        Workshop drillPW = new Workshop();
        drillPW.setName("drill up");
        drill.getWorkshops().add(drillPW);
        Workshop drillSW = new Workshop();
        drillSW.setName("drill up 2");
        drill.getWorkshops().add(drillSW);
        pod.getRooms().add(drill);


        // Difficulty
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
