package app.BasketballStats.controller;
import app.BasketballStats.model.AppUser;
import app.BasketballStats.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins ="http://localhost:3000")
public class AuthController {

    @Autowired
    private AppUserRepository userRepository;
    //register
    @PostMapping
    public AppUser register(@RequestBody AppUser user) {
        return userRepository.save(user);
    }
    // Check for login
    @GetMapping("/check")
    public Map<String, Boolean> check(@RequestParam String username, @RequestParam String password) {
        boolean exists = userRepository.findByUsernameAndPassword(username, password).isPresent();
        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", exists);
        return response;
    }
}
