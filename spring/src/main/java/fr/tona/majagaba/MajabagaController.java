package fr.tona.majagaba;

import fr.tona.util.DieAction;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/majagaban")
@RequiredArgsConstructor
public class MajabagaController {

    private final MajagabaService service;

    @GetMapping("/reroll")
    public void reroll(){
        service.reroll();
    }

    @PutMapping("/stock-die")
    public void stockDie(@RequestBody Integer value){
        service.stockDie(value);
    }

    @PutMapping("/destock-die")
    public void destockDie(@RequestBody Integer value){
        service.destockDie(value);
    }

    @PutMapping("/move")
    public void move(@RequestBody DieAction action){
        service.move(action);
    }

    @PutMapping("/allocate")
    public void allocate(@RequestBody DieAction action){
        service.allocate(action);
    }

    @PutMapping("/take-object")
    public void takeObject(@RequestBody String objectName){
        service.takeObject(objectName);
    }

}
