package fr.tona.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.tona.expedition.Expedition;
import fr.tona.pod.Pod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne
    @JsonIgnoreProperties("room")
    private Pod pod;

    public String name;
    public Integer health;
    public String status;

}
