package app.BasketballStats;
import app.BasketballStats.model.Game;
import app.BasketballStats.repository.GameRepository;
import app.BasketballStats.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

public class GameServiceIntegrationTest {
    @Autowired private GameService gameService;
    @Autowired private GameRepository gameRepository;
    private Game testGame;
    @BeforeEach
    public void setup() {
        gameRepository.deleteAll();
        testGame= new Game();
        testGame.setDate(LocalDate.of(2024, 6, 1));
        testGame.setOpponent("Test Opponent");
    }

    @Test
    public void testCreateGame() {
        Game savedGame =gameService.createGame(testGame);
        assertNotNull(savedGame.getId());
    }

    @Test
    public void testGetGameById() {
        Game savedGame = gameService.createGame(testGame);
        Optional<Game> foundGame = gameService.getGameById(savedGame.getId());
        assertTrue(foundGame.isPresent());
    }
    @Test
    public void testGetAllGames() {
        gameService.createGame(testGame);
        List<Game> games = gameService.getAllGames();
        assertEquals(1, games.size());
    }
    @Test
    public void testDeleteGame() {
        Game savedGame=gameService.createGame(testGame);
        gameService.deleteGame(savedGame.getId());
        assertFalse(gameRepository.findById(savedGame.getId()).isPresent());
    }
}
