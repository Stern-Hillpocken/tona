package fr.tona.podregister;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PodRegisterService {

    private final PodRegisterRepository repository;

    public List<PodRegister> getAllReady() {
        return repository.findAll();
    }

    public Map<String, String> prepareNewOne(PodRegister pod) {
        repository.save(pod);
        Map<String, String> response = new HashMap<>();
        response.put("message","Ta nouvelle nacelle est prête !");
        response.put("type","work");
        return response;
    }
}
