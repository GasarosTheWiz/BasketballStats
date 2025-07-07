package app.BasketballStats.controller;
import app.BasketballStats.model.PlayerStats;
import app.BasketballStats.repository.PlayerStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playerstats")
@CrossOrigin(origins ="http://localhost:3000")
public class PlayerStatsController {
    @Autowired
    private PlayerStatsRepository playerStatsRepository;
    
    @PostMapping(produces = "application/json")
    public ResponseEntity<PlayerStats> createPlayerStats(@RequestBody PlayerStats stats) {
        PlayerStats savedStats = playerStatsRepository.save(stats);
        return ResponseEntity.ok(savedStats);
    }
    
    @GetMapping(produces ="application/json")
    public ResponseEntity<List<PlayerStats>> getAllPlayerStats() {
        List<PlayerStats> statsList = playerStatsRepository.findAll();
        return ResponseEntity.ok(statsList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayerStats(@PathVariable Long id) {
        if (!playerStatsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        playerStatsRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
