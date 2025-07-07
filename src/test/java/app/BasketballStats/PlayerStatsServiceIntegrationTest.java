package app.BasketballStats;

import app.BasketballStats.model.Game;
import app.BasketballStats.model.Player;
import app.BasketballStats.model.PlayerStats;
import app.BasketballStats.repository.GameRepository;
import app.BasketballStats.repository.PlayerRepository;
import app.BasketballStats.repository.PlayerStatsRepository;
import app.BasketballStats.service.PlayerStatsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class PlayerStatsServiceIntegrationTest {

    @Autowired private PlayerStatsService playerStatsService;
    @Autowired private PlayerStatsRepository statsRepo;
    @Autowired private PlayerRepository playerRepo;
    @Autowired private GameRepository gameRepo;
    private Player player;
    private Game game;
    private PlayerStats stats;
    @BeforeEach
    public void setup() {
        statsRepo.deleteAll();
        playerRepo.deleteAll();
        gameRepo.deleteAll();
        player = playerRepo.save(new Player("Test Player","PG", 5));
        game = gameRepo.save(new Game());
        game.setDate(LocalDate.of(2024, 6,1));
        game.setOpponent("Test Opponent");
        game = gameRepo.save(game);
        stats = new PlayerStats();
        stats.setPlayer(player);
        stats.setGame(game);
        stats.setTwoPScored(5);
        stats.setTwoPShot(10);
        stats.setThreePScored(2);
        stats.setThreePShot(5);
        stats.setFtScored(4);
        stats.setFtShot(6);
    }

    @Test
    public void testCreateStats() {
        PlayerStats saved = playerStatsService.createStats(stats);
        assertNotNull(saved.getId());
    }
    @Test
    public void testGetStatsById() {
        PlayerStats saved =playerStatsService.createStats(stats);
        assertTrue(playerStatsService.getStatsById(saved.getId()).isPresent());
    }
    @Test
    public void testGetAllStats() {
        playerStatsService.createStats(stats);
        assertEquals(1, playerStatsService.getAllStats().size());
    }
    @Test
    public void testDeleteStats() {
        PlayerStats saved= playerStatsService.createStats(stats);
        playerStatsService.deleteStats(saved.getId());
        assertFalse(playerStatsService.getStatsById(saved.getId()).isPresent());
    }
}
