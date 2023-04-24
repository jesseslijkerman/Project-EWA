package app.rest;

import app.APIConfig;
import app.exceptions.NotAcceptable;
import app.security.JWToken;
import app.models.User;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

    @Autowired
    APIConfig apiConfig;

    @PostMapping(path = "login")
    public ResponseEntity<User> logIn(@RequestBody ObjectNode signInInfo){
        String email = signInInfo.get("email").asText();
        String password = signInInfo.get("password").asText();

        String[] emailSplit = email.split("@");

        if (Objects.equals(password, emailSplit[0])){
            User user = new User(1L + (long) (Math.random() * 100L) , emailSplit[0], email, "registered user", password);
            JWToken jwToken = new JWToken(user.getName(), user.getId(), user.getRole());

            String tokenString = jwToken.encode(
                    this.apiConfig.getIssuer(),
                    this.apiConfig.getPassphrase(),
                    this.apiConfig.getTokenDurationOfValidity()
            );

            return ResponseEntity.accepted()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenString)
                    .body(user);
        } else{
            throw new NotAcceptable("Cannot authenticate user by email=" + email);
        }

    }
}
