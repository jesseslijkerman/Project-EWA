package app.controllers;

import app.models.User;
import app.repositories.UsersRepository;
import app.security.JWToken;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "/usersAuth")
public class AuthenticationController {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final JWToken jwt = new JWToken();

    @Autowired
    public AuthenticationController(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @PostMapping(path = "/login")
    @CrossOrigin
    public ResponseEntity<AuthResponse> authenticateUser(@RequestBody JsonNode request) {
        String email = request.get("email").asText();
        String password = request.get("password").asText();

        User user = usersRepository.findByEmail(email);
        boolean isValidPassword = encoder.matches(password, user.getPassword());

        if (!isValidPassword) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            AuthResponse authResponse = createAuthResponse(user);
            return ResponseEntity.accepted().header("Authorization", authResponse.token()).body(authResponse);
        }
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<Void> userLogout() {
        jwt.logOut();
        return ResponseEntity.ok().build();
    }

    private AuthResponse createAuthResponse(User user) {
        String token = jwt.createToken(user.getId(), user.getUsername(), user.getCountryCode(), user.getPassword(), user.getEmail());
        return new AuthResponse(user.getId(), user.getUsername(), user.getCountryCode(), user.getEmail(), user.getPassword(), token);
    }

    private record AuthResponse(long id, String userName, String countryCode, String email, String password,
                                String token) {
    }
}
