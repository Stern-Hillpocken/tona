package fr.tona.expedition;

//import fr.tona.pod.Pod;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.tona.chatmessage.ChatMessage;
import fr.tona.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // When you have entities that are loaded lazily before the serialization then the entities get loaded fully
public class Expedition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer difficulty;

    private Long day;
    private Integer hour;
    private Integer minute;

    //public Pod pod;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JsonIgnoreProperties("expedition")
    private User captain;
    //public Set<Majagaba> crew;

    private Long water;
    private Long depth;
    @OneToOne(cascade = {CascadeType.MERGE})
    @JsonIgnoreProperties("expedition")
    private List<ChatMessage> messages;
    private String status;
}
