package fr.tona.expedition;

import fr.tona.character.Character;
import fr.tona.pod.Pod;
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
public class Expedition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String name;
    public Integer difficulty;

    public Long day;
    public Integer hour;
    public Integer minute;

    public Pod pod;

    public User captain;
    public Set<Character> crew;

    public String status;
}
