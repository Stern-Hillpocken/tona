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
    private Integer minute = 15;

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
    private List<User> crew = new ArrayList<>();

    private Long water = 0L;
    private Integer scrap = 25;

    private Integer augerPosition = 0;
    private Integer[] veinReal = new Integer[]{0,0,0};
    private Integer[][] veinSurvey = new Integer[][]{new Integer[]{0,0},new Integer[]{0,0},new Integer[]{0,0}};
    private Integer[] veinScrapAndWater = new Integer[]{0,0};
    private Integer[][] veinScrapAndWaterSurvey = new Integer[][]{new Integer[]{0,0},new Integer[]{0,0}};
    private Integer probeScanningTimes = 0;

    private Integer ammo = 6;
    private Integer[] enemiesZoneBasic = new Integer[]{0,0,0,0,0,0};
    private Integer[] enemiesZoneSpeedy = new Integer[]{0,0,0,0,0,0};
    private Integer[] enemiesZoneThrower = new Integer[]{0,0,0,0,0,0};

    private Integer[] radarCrankLevel = new Integer[]{0,0}; // position & type
    private Integer[] enemiesZoneBasicRadared = new Integer[]{0,0,0,0,0,0,0};
    private Integer[] enemiesZoneSpeedyRadared = new Integer[]{0,0,0,0,0,0,0};
    private Integer[] enemiesZoneThrowerRadared = new Integer[]{0,0,0,0,0,0,0};
    private Integer spiceDoseCrankLevel = 2;
    private Integer[] hullDiagnosticPanelCrankLevel = new Integer[]{0,0}; // localisation & status
    private String[] nextRoomsEventTargeted = new String[]{"","","","","",""};
    private String[] nextRoomsStatus = new String[]{"","",""};

    private Long depth = 10L;

    private Integer blastedDice = 0;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "expedition_id", referencedColumnName = "id")
    @JsonIgnoreProperties("expedition")
    private List<ChatMessage> messages = new ArrayList<>();

    private String status = "in-game";
}
