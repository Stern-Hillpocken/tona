package fr.tona.expedition;

import fr.tona.chat_message.ChatMessage;
import fr.tona.pod_register.PodRegister;
import fr.tona.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

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
        expedition.setMessages(new HashSet<>());
        expedition.setDepth(0L);
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

    public Set<ChatMessage> getAllChatMessages() {
        //return chatMessageRepository.findAll();
        //return repository.findById(1L).get().getMessages();
//        List<ChatMessage> allMessages = chatMessageRepository.findAll();
//        for(int i = 0; i < allMessages.size(); i++){
//            System.out.println(allMessages.get(i).getContents());
//        }
//        return allMessages;
        return repository.findById(1L).get().getMessages();
    }

    public void sendMessage(String messageContents) {
        ChatMessage fullMessage = new ChatMessage();
        User blankUser = new User();
        blankUser.setId(1L);
        fullMessage.setUser(blankUser);

        String pattern = "yyyy-MM-dd'T'HH:mm:ss";
        DateFormat df = new SimpleDateFormat(pattern);
        Date today = Calendar.getInstance().getTime();
        String now = df.format(today);
        fullMessage.setDate(now);

        fullMessage.setContents(messageContents);

        Expedition currentExpedition = repository.findById(1L).orElseThrow(
                () -> new RuntimeException("Id of expedition not found")
        );
        currentExpedition.getMessages().add(fullMessage);
        repository.save(currentExpedition);
        // expeditionRepo.findById(Id) = id de l'expédition envoyé par le front et récupéré en @PathVariable
        // expedition.getMessages().add(fullMessage)
        // expeditionRepo.save(expedition)
    }
}
