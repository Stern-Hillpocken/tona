package fr.tona.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.tona.expedition.Expedition;
import fr.tona.pod.Pod;
import fr.tona.util.Workshop;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    public Long id;

    @ManyToOne
    @JsonIgnore
    private Pod pod;

    private String name;
    private Integer health = 6;
    private Integer maxHealth = 6;
    private String status = "";

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("room")
    @JoinColumn(name = "room_id")
    private List<Workshop> workshops = new ArrayList<>();

}
