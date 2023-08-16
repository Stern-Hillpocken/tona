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

    @PutMapping("/armory/reload")
    public void armoryReload(@RequestBody DieAction action){
        service.armoryReload(action);
    }


    @PutMapping("/porthole/radar-position")
    public void radarPosition(@RequestBody DieAction action){
        service.radarPosition(action);
    }

    @PutMapping("/porthole/radar-type")
    public void radarType(@RequestBody DieAction action){
        service.radarType(action);
    }

    @PutMapping("/porthole/spice-prepare")
    public void spicePrepare(@RequestBody DieAction action){
        service.spicePrepare(action);
    }

    @PutMapping("/porthole/spice-prepare-and-take")
    public void spicePrepareAndTake(@RequestBody DieAction action){
        service.spicePrepareAndTake(action);
    }

    @PutMapping("/porthole/spice-take")
    public void spiceTake(@RequestBody DieAction action){
        service.spiceTake(action);
    }

    @PutMapping("/porthole/hull-diagnostic-localisation")
    public void hullDiagnosticLocalisation(@RequestBody DieAction action){
        service.hullDiagnosticLocalisation(action);
    }

    @PutMapping("/porthole/hull-diagnostic-status")
    public void hullDiagnosticStatus(@RequestBody DieAction action){
        service.hullDiagnosticStatus(action);
    }

    @PutMapping("/reparation")
    public void roomReparation(@RequestBody DieAction action){
        service.roomReparation(action);
    }
}
