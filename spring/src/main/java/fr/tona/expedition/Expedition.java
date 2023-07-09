package fr.tona.expedition;

//import fr.tona.pod.Pod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.tona.chat_message.ChatMessage;
import fr.tona.majagaba.Majagaba;
import fr.tona.pod.Pod;
import fr.tona.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Expedition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Integer difficulty;

    private Long day = 0L;
    private Integer hour = 0;
    private Integer minute = 0;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})// Need persist when created during Expe creation
    @JsonIgnoreProperties(value = {"expedition"})
    //@JsonIgnore
    private Pod pod;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties(value = {"expedition", "pod"})
    private User captain;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "expedition_id", referencedColumnName = "id")
    @JsonIgnoreProperties("expedition")
    private Set<User> crew = new HashSet<>();

    private Long water = 0L;
    private Long depth = 0L;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "expedition_id", referencedColumnName = "id")
    @JsonIgnoreProperties("expedition")
    private List<ChatMessage> messages = new ArrayList<>();

    private String status = "in-game";
}
