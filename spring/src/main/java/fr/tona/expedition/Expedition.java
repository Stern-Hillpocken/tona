package fr.tona.expedition;

//import fr.tona.pod.Pod;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.tona.chat_message.ChatMessage;
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

    private Long day;
    private Integer hour;
    private Integer minute;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JsonIgnoreProperties(value = {"expedition", "pod"})
    private User captain;

    private Long water;
    private Long depth;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "expedition_id", referencedColumnName = "id")
    @JsonIgnoreProperties("expedition")
    private List<ChatMessage> messages;

    private String status;
}
