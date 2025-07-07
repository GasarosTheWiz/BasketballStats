package app.BasketballStats.controller;

import app.BasketballStats.model.Player;
import app.BasketballStats.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/players")
@CrossOrigin(origins= "http://localhost:3000")
public class PlayerController {

    private final PlayerService playerService;
    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService =playerService;
    }

    @PostMapping("/save")
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) {
        Player savedPlayer = playerService.createPlayer(player);
        return ResponseEntity.ok(savedPlayer);
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Player>> getAllPlayers() {
        List<Player> players = playerService.getAllPlayers();
        return ResponseEntity.ok(players);
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlayer(@PathVariable Long id) {
        if (playerService.getPlayerById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        playerService.deletePlayer(id);
        return ResponseEntity.noContent().build();
    }
}
