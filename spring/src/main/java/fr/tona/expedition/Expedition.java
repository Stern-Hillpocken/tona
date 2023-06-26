package fr.tona.expedition;

//import fr.tona.pod.Pod;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.tona.chatmessage.ChatMessage;
import fr.tona.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    @JsonIgnoreProperties("expedition")
    private User captain;

    private Long water;
    private Long depth;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "expedition_id", referencedColumnName = "id")
    @JsonIgnoreProperties("expedition")
    private Set<ChatMessage> messages = new HashSet<>();

    private String status;
}
