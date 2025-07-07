package app.BasketballStats;

import app.BasketballStats.model.Player;
import app.BasketballStats.repository.PlayerRepository;
import app.BasketballStats.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PlayerServiceIntegrationTest {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerRepository playerRepository;
    @BeforeEach
    public void setup() {
        playerRepository.deleteAll();
    }
    @Test
    public void testCreateAndGetPlayer() {
        Player player = new Player("John Doe", "Guard", 23);

        Player savedPlayer= playerService.createPlayer(player);
        assertThat(savedPlayer.getId()).isNotNull();
        assertThat(savedPlayer.getName()).isEqualTo("John Doe");

        Optional<Player> foundPlayer =playerService.getPlayerById(savedPlayer.getId());
        assertThat(foundPlayer).isPresent();
        assertThat(foundPlayer.get().getName()).isEqualTo("John Doe");
    }

    @Test
    public void testGetAllPlayers() {
        Player p1 = new Player("One","Forward", 10);
        Player p2 = new Player("Two", "Center",15);
        playerService.createPlayer(p1);
        playerService.createPlayer(p2);

        List<Player> players = playerService.getAllPlayers();
        assertThat(players.size()).isEqualTo(2);
        assertThat(players).extracting(Player::getName)
                           .containsExactlyInAnyOrder("One", "Two");
    }

    @Test
    public void testDeletePlayer() {
        Player player =new Player("ToDelete", "Guard", 9);
        Player savedPlayer = playerService.createPlayer(player);
        playerService.deletePlayer(savedPlayer.getId());
        Optional<Player> found = playerService.getPlayerById(savedPlayer.getId());
        assertThat(found).isNotPresent();
    }
}
