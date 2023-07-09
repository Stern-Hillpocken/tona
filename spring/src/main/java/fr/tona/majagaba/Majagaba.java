package fr.tona.majagaba;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.tona.expedition.Expedition;
import fr.tona.pod_register.PodRegister;
import fr.tona.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    @OneToOne(cascade = {CascadeType.MERGE}, mappedBy = "majagaba")
    @JsonIgnore
    private User user;

    private Integer life = 5;
    private Integer maxLife = 5;
    private String job;
    private List<Integer> dicePool = new ArrayList<>();
    private List<Integer> diceStocked = new ArrayList<>();
    private Integer rerollLeft = 2;
    private String room;

    @ManyToOne
    @JsonIgnore
    private Expedition expedition;
}
