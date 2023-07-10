package fr.tona.majagaba;

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

}
