package app.BasketballStats.controller;
import app.BasketballStats.dto.CombinedInsertDTO;
import app.BasketballStats.dto.CombinedUpdateDTO;
import app.BasketballStats.service.CombinedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/combined")
@CrossOrigin(origins = "http://localhost:3000")
public class CombinedController {
    private final CombinedService combinedService;
    public CombinedController(CombinedService combinedService) {
        this.combinedService=combinedService;
    }
    // create combined Player+ Game +Stats
    @PostMapping(value = "/create",consumes ="application/json")
    public ResponseEntity<Void> createCombined(@RequestBody CombinedInsertDTO dto) {
        try {
            combinedService.createCombinedEntry(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    //update combined Εntr
    @PutMapping(value ="/update", consumes = "application/json")
    public ResponseEntity<Void> updateCombined(@RequestBody CombinedUpdateDTO dto) {
        try {
            combinedService.updateCombinedEntry(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    // Delete all by ids
    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteAll(
            @RequestParam Long playerId,
            @RequestParam Long gameId,
            @RequestParam Long statsId
    ) {
        try {
            combinedService.deleteAllByIds(playerId, gameId,statsId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
