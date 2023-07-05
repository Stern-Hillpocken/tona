package fr.tona.pod_register;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/pod-setup")
@RequiredArgsConstructor
public class PodRegisterController {

    private final PodRegisterService service;

    @GetMapping("/all-ready")
    public List<PodRegister> getAllReady(){
        return service.getAllReady();
    }

    @PostMapping("/prepare-new-one")
    public Map<String, String> prepareNewOne(@RequestBody PodRegister pod){
        return service.prepareNewOne(pod);
    }

    @DeleteMapping("/destroy-own")
    public Map<String, String> destroyOwn(){
        return service.destroyOwn();
    }

    @DeleteMapping("/launch")
    public Map<String, String> launch(){
        return service.launch();
    }
}
