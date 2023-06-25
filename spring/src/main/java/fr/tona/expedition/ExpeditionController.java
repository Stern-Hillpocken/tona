package fr.tona.expedition;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
