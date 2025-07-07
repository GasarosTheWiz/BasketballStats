package app.BasketballStats;
import app.BasketballStats.dto.CombinedInsertDTO;
import app.BasketballStats.dto.CombinedUpdateDTO;
import app.BasketballStats.model.Game;
import app.BasketballStats.model.Player;
import app.BasketballStats.model.PlayerStats;
import app.BasketballStats.repository.GameRepository;
import app.BasketballStats.repository.PlayerRepository;
import app.BasketballStats.repository.PlayerStatsRepository;
import app.BasketballStats.service.CombinedService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CombinedServiceIntegrationTest {
    @Autowired private CombinedService combinedService;
    @Autowired private PlayerRepository playerRepository;
    @Autowired private GameRepository gameRepository;
    @Autowired private PlayerStatsRepository statsRepository;

    @Test
    public void testCreateUpdateAndDeleteCombinedEntry() {
        //create combined entryPlayer + Game +Stats
        CombinedInsertDTO createDto =new CombinedInsertDTO(
                "John Doe","PG",9, 7,14, 3, 7, 5, 6,
                "2025-06-28","Olympiakos"
        );
        combinedService.createCombinedEntry(createDto);
        // check creation
        Player player = playerRepository.findAll().stream()
                .filter(p -> "John Doe".equals(p.getName()))
                .findFirst().orElseThrow();
        Game game = gameRepository.findAll().stream()
                .filter(g -> "Olympiakos".equals(g.getOpponent()))
                .findFirst().orElseThrow();
        PlayerStats stats = statsRepository.findAll().stream()
                .filter(s -> s.getPlayer().getId().equals(player.getId()) &&
                             s.getGame().getId().equals(game.getId()))
                .findFirst().orElseThrow();
        assertEquals(7, stats.getTwoPScored());
        // pdate combined entry
        CombinedUpdateDTO updateDto = new CombinedUpdateDTO(
                player.getId(), game.getId(),stats.getId(),
                "Jane Smith","SG", 10,
                "Panathinaikos","2025-07-01",
                10,15,4, 8, 6, 7
        );
        combinedService.updateCombinedEntry(updateDto);
        //check update
        assertEquals("Jane Smith", playerRepository.findById(player.getId()).orElseThrow().getName());
        assertEquals("Panathinaikos", gameRepository.findById(game.getId()).orElseThrow().getOpponent());
        // delete combined entry
        combinedService.deleteAllByIds(player.getId(), game.getId(), stats.getId());
        assertFalse(playerRepository.existsById(player.getId()));
        assertFalse(gameRepository.existsById(game.getId()));
        assertFalse(statsRepository.existsById(stats.getId()));
    }
}
