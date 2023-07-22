package fr.tona.pod_register;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import fr.tona.user.User;
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
public class PodRegister {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String name;
    private Integer difficulty;

    @OneToOne(cascade = {CascadeType.MERGE})
    @JsonIgnoreProperties("pod")
    private User captain;
    /*@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "podRegister_id", referencedColumnName = "id")
    @JsonIgnoreProperties("pod")
    public Set<Majagaba> crew = new HashSet<>();*/

    private Integer characterMax;
    private String status;

    @JsonIgnore
    private String password;
}
