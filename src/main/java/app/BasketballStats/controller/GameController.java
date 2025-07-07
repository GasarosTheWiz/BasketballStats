package app.BasketballStats.controller;
import app.BasketballStats.model.Game;
import app.BasketballStats.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/games")
@CrossOrigin(origins ="http://localhost:3000")
public class GameController {
    @Autowired
    private GameRepository gameRepository;
    @PostMapping(consumes = "application/json",produces ="application/json")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game savedGame= gameRepository.save(game);
        return ResponseEntity.ok(savedGame);
    }
    @GetMapping(produces= "application/json")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games =gameRepository.findAll();
        return ResponseEntity.ok(games);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        if (!gameRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        gameRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
