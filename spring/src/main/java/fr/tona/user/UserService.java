package fr.tona.user;

import fr.tona.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final JwtService jwtService;


    public User getMe() {
        return jwtService.grepUserFromJwt();
    }

    public void changeProfilePicture(String url) {
        User user = jwtService.grepUserFromJwt();
        user.setProfilePicture(url);
        repository.save(user);
    }
}