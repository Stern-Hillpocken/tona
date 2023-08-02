package fr.tona.expedition;

import fr.tona.chat_message.ChatMessage;
import fr.tona.util.DieAction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/expedition")
@RequiredArgsConstructor
public class ExpeditionController {

    private final ExpeditionService service;

    @GetMapping("/my")
    public Expedition getMy(){
        return service.getMy();
    }

    @GetMapping("/end-turn")
    public Expedition endTurn(){
        return service.endTurn();
    }

    @GetMapping("/all-chat-messages")
    public List<ChatMessage> getAllChatMessages(){
        return service.getAllChatMessages();
    }

    @PostMapping("/send-message")
    public void sendMessage(@RequestBody String messageContents){
        service.sendMessage(messageContents);
    }

    @GetMapping("/use-object/steam-blast")
    public void useSteamBlast(){
        service.useSteamBlast();
    }

    @PutMapping("/extractor/auger-increase")
    public void augerIncrease(@RequestBody DieAction action){
        service.augerIncrease(action);
    }

    @PutMapping("/extractor/probe-scan")
    public void probeScan(@RequestBody DieAction action){
        service.probeScan(action);
    }

    @PutMapping("/armory/shoot-enemy")
    public void armoryShoot(@RequestBody DieAction action){
        service.armoryShoot(action);
    }
}
