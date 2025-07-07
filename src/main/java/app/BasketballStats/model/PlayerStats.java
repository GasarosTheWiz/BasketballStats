package app.BasketballStats.model;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@JsonIdentityInfo(
    generator =ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class PlayerStats {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private int twoPScored;
    private int twoPShot;
    private int threePScored;
    private int threePShot;
    private int ftScored;
    private int ftShot;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    @ManyToOne
    @JoinColumn(name ="game_id")
    private Game game;

    public Long getId() {return id; }
    public void setId(Long id) {this.id = id; }

    public int getTwoPScored(){ return twoPScored; }
    public void setTwoPScored(int twoPScored) { this.twoPScored = twoPScored; }

    public int getTwoPShot() {return twoPShot; }
    public void setTwoPShot(int twoPShot) { this.twoPShot = twoPShot; }

    public int getThreePScored() { return threePScored; }
    public void setThreePScored(int threePScored) { this.threePScored = threePScored; }

    public int getThreePShot() { return threePShot; }
    public void setThreePShot(int threePShot) { this.threePShot = threePShot; }

    public int getFtScored() {return ftScored; }
    public void setFtScored(int ftScored) { this.ftScored = ftScored; }

    public int getFtShot() { return ftShot; }
    public void setFtShot(int ftShot) { this.ftShot = ftShot; }

    public Player getPlayer(){return player; }
    public void setPlayer(Player player) { this.player = player; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }
}
