package app.BasketballStats.service;

import app.BasketballStats.model.Player;
import app.BasketballStats.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    
    
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}
