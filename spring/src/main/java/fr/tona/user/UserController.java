package fr.tona.user;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository repository;

    @GetMapping("/all")
    public List<User> getAll(HttpServletRequest request) throws AccessDeniedException {
        String role  = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if(role.equals("[ROLE_ADMIN]")) {
            return repository.findAll();
        } else {
            request.setAttribute("access_denied", "You do not have suffisant rights to access to this resource");
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }

    @GetMapping("/id/{id}")
    public User getById(@PathVariable Long id){
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("User ID: "+id+" has not be founded")
        );
    }

    @GetMapping("/profilePicture/{id}")
    public Map<String,String> getProfilePicture(@PathVariable Long id){
        String profilePicture = repository.findById(id).get().getProfilePicture();
        Map<String, String> jsonReturned = new HashMap<>();
        jsonReturned.put("property",profilePicture);
        return jsonReturned;
    }
}