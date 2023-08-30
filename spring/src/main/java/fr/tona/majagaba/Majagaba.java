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
    @JsonIgnore
    private Long id;

    @OneToOne(cascade = {CascadeType.MERGE}, mappedBy = "majagaba")
    @JsonIgnore
    private User user;

    private Integer life = 6;
    private Integer maxLife = 6;
    private String job = "miner";
    private Boolean isJobActivated = false;
    private List<Integer> dicePool = new ArrayList<>();
    private List<Integer> diceStocked = new ArrayList<>();
    private List<Integer> diceNextTurn = new ArrayList<>();
    private Integer rerollLeft = 2;
    private String room;
    // craftable objects
    private Integer steamBlast = 1;
    private Integer steamRegulator = 3;
    private Integer steamSwitcher = 2;

    private Integer powerCharge = 3;
    private Integer powerChargeMax = 3;


    @ManyToOne
    @JsonIgnore
    private Expedition expedition;
}
