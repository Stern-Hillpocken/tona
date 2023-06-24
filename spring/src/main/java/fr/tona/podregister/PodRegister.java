package fr.tona.podregister;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.tona.character.Character;
import fr.tona.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PodRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public Integer difficulty;
    public User captain;
    public Set<Character> crew;
    public Integer playerMax;
    @JsonIgnore
    public String password;
    public String status;
}
