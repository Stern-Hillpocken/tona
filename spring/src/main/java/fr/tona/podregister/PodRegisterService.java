package fr.tona.podregister;

import fr.tona.expedition.ExpeditionService;
import fr.tona.user.User;
import fr.tona.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class PodRegisterService {

    private final PodRegisterRepository repository;
    private final UserRepository userRepository;
    private final ExpeditionService expeditionService;

    public List<PodRegister> getAllReady() {
        return repository.findAll();
    }

    public Map<String, String> prepareNewOne(PodRegister pod) {
        // TODO Controle pod sended
        User captain = new User();
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.findByPseudo(username).orElseThrow(
                () -> new RuntimeException("The pseudo is not in the data base")
        ).getId();
        captain.setId(userId);
        pod.setStatus("waiting...");
        pod.setCaptain(captain);
        repository.save(pod);

        Map<String, String> response = new HashMap<>();
        response.put("message","Ta nouvelle nacelle est prête \uD83C\uDFC1");
        response.put("type","work");
        return response;
    }

    public Map<String, String> destroyOwn() {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByPseudo(username).orElseThrow(
                () -> new RuntimeException("The pseudo is not in the data base")
        );
        repository.deleteById(user.getPod().getId());

        Map<String, String> response = new HashMap<>();
        response.put("message","Nacelle démontée \uD83E\uDDE9");
        response.put("type","work");
        return response;
    }

    public Map<String, String> launch() {
        // Find pod
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByPseudo(username).orElseThrow(
                () -> new RuntimeException("The pseudo is not in the data base")
        );
        // Save pod
        PodRegister podRegister = user.getPod();
        // Delete pod
        repository.deleteById(user.getPod().getId());

        // Create expedition
        expeditionService.launch(podRegister, user);

        // If no error, return popup
        Map<String, String> response = new HashMap<>();
        response.put("message","Nacelle en descente ⏬");
        response.put("type","work");
        return response;
    }
}
