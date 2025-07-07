package app.BasketballStats.service;

import app.BasketballStats.model.PlayerStats;
import app.BasketballStats.repository.PlayerStatsRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PlayerStatsService {

    private final PlayerStatsRepository statsRepository;

    public PlayerStatsService(PlayerStatsRepository statsRepository) {
        this.statsRepository=statsRepository;
    }
    public List<PlayerStats> getAllStats() {
        return statsRepository.findAll();
    }

    public Optional<PlayerStats> getStatsById(Long id) {
        return statsRepository.findById(id);
    }
    public PlayerStats createStats(PlayerStats stats) {
        return statsRepository.save(stats);
    }
    
    public void deleteStats(Long id) {
        statsRepository.deleteById(id);
    }
}
