package app.BasketballStats.controller;
import app.BasketballStats.model.AppUser;
import app.BasketballStats.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins ="http://localhost:3000")
public class AuthController {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Register user
    @PostMapping
    public ResponseEntity<AppUser> register(@RequestBody AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        AppUser savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    //check login
    @GetMapping("/check")
    public ResponseEntity<Map<String, Boolean>> check(@RequestParam String username, @RequestParam String password) {
        boolean valid = userRepository.findByUsername(username)
            .map(dbUser -> passwordEncoder.matches(password, dbUser.getPassword()))
            .orElse(false);

        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", valid);
        return ResponseEntity.ok(response);
    }
}
