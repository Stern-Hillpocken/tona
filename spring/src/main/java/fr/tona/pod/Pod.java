package fr.tona.pod;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.tona.expedition.Expedition;
import fr.tona.room.Room;
import jakarta.persistence.*;
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
public class Pod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @OneToOne(mappedBy = "pod")
    @JsonIgnoreProperties("pod")
    private Expedition expedition;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pod_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"pod"})
    private Set<Room> rooms;

    public Integer health;
    public Integer maxHealth = 10;

}
