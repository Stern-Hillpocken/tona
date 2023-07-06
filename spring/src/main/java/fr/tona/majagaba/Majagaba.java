package fr.tona.majagaba;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.tona.expedition.Expedition;
import fr.tona.pod_register.PodRegister;
import fr.tona.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Majagaba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "majagaba")
    @JsonIgnoreProperties(value = {"majagaba", "expedition"})
    private User user;

    private Integer life;
    private String job;
    private List<Integer> dicePool;
    private List<Integer> diceStocked;
    private Integer rerollLeft;
    private String room;

    @ManyToOne
    @JsonIgnoreProperties("crew")
    private Expedition expedition;
}
