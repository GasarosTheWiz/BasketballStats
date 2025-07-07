package app.BasketballStats.service;
import app.BasketballStats.dto.CombinedInsertDTO;
import app.BasketballStats.dto.CombinedUpdateDTO;
import app.BasketballStats.model.Game;
import app.BasketballStats.model.Player;
import app.BasketballStats.model.PlayerStats;
import app.BasketballStats.repository.GameRepository;
import app.BasketballStats.repository.PlayerRepository;
import app.BasketballStats.repository.PlayerStatsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class CombinedService {
    private static final Logger logger= LoggerFactory.getLogger(CombinedService.class);
    private final PlayerRepository playerRepository;
    private final GameRepository gameRepository;
    private final PlayerStatsRepository statsRepository;

    public CombinedService(PlayerRepository playerRepository,
                           GameRepository gameRepository,
                           PlayerStatsRepository statsRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.statsRepository = statsRepository;
    }
    
    
    public void createCombinedEntry(CombinedInsertDTO dto) {
        Player player =new Player();
        player.setName(dto.playerName());
        player.setPosition(dto.position());
        player.setNumber(dto.number());
        player= playerRepository.save(player);

        Game game =new Game();
        LocalDate date = LocalDate.parse(dto.gameDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        game.setDate(date);
        game.setOpponent(dto.opponent());
        game = gameRepository.save(game);

        PlayerStats stats = new PlayerStats();
        stats.setPlayer(player);
        stats.setGame(game);
        stats.setTwoPScored(dto.twoPScored());
        stats.setTwoPShot(dto.twoPShot());
        stats.setThreePScored(dto.threePScored());
        stats.setThreePShot(dto.threePShot());
        stats.setFtScored(dto.ftScored());
        stats.setFtShot(dto.ftShot());

        statsRepository.save(stats);
    }
    
    public void updateCombinedEntry(CombinedUpdateDTO dto) {
        Player player = playerRepository.findById(dto.playerId())
                .orElseThrow(() -> new RuntimeException("Player not found"));
        player.setName(dto.playerName());
        player.setPosition(dto.position());
        player.setNumber(dto.number());
        playerRepository.save(player);

        Game game = gameRepository.findById(dto.gameId())
                .orElseThrow(() -> new RuntimeException("Game not found"));
        LocalDate date = LocalDate.parse(dto.gameDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        game.setDate(date);
        game.setOpponent(dto.opponent());
        gameRepository.save(game);
        PlayerStats stats = statsRepository.findById(dto.statsId())
                .orElseThrow(() -> new RuntimeException("Stats not found"));
        stats.setTwoPScored(dto.twoPScored());
        stats.setTwoPShot(dto.twoPShot());
        stats.setThreePScored(dto.threePScored());
        stats.setThreePShot(dto.threePShot());
        stats.setFtScored(dto.ftScored());
        stats.setFtShot(dto.ftShot());
        statsRepository.save(stats);
    }

    public void deleteAllByIds(Long playerId, Long gameId, Long statsId) {
        if (statsRepository.existsById(statsId)){
            statsRepository.deleteById(statsId);
            logger.info("Deleted stats with id={}",statsId);
        }
        if (playerRepository.existsById(playerId)) {
            playerRepository.deleteById(playerId);
            logger.info("Deleted player with id={}",playerId);
        }
        if (gameRepository.existsById(gameId)) {
            gameRepository.deleteById(gameId);
            logger.info("Deleted game with id={}", gameId);
        }
    }
}
