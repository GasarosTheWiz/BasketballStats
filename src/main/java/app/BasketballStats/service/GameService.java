package app.BasketballStats.service;
import app.BasketballStats.model.Game;
import app.BasketballStats.repository.GameRepository;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;

@Service
public class GameService {

    private final GameRepository gameRepository;
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    
    public Optional<Game> getGameById(Long id) {
        return gameRepository.findById(id);
    }
    
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }
    
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}
