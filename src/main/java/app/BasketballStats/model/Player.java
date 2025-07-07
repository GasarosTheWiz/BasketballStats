package app.BasketballStats.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.List;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property ="id"
)
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String position;
    private Integer number;
    
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlayerStats> playerStats;

    public Player() {}

    public Player(String name, String position, Integer number) {
        this.name = name;
        this.position = position;
        this.number = number;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName(){ return name; }
    public void setName(String name) { this.name = name; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    public Integer getNumber() { return number; }
    public void setNumber(Integer number) { this.number = number; }

    public List<PlayerStats> getPlayerStats(){ return playerStats; }
    public void setPlayerStats(List<PlayerStats> playerStats) { this.playerStats = playerStats; }
}
