package app.controllers;

import app.models.Lobby;
import app.models.UserLobby;
import app.repositories.UserLobbyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/userLobbies")
public class UserLobbyController {
    @Autowired
    private UserLobbyRepository userLobbyRepo;

    @GetMapping(path = "", produces = "application/json")
    public List<UserLobby> getAllLobbys(){
        return this.userLobbyRepo.findAll();
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> createUserLobby(@RequestBody UserLobby lobby){
        UserLobby createdUserLobby = userLobbyRepo.save(lobby);

        // Return appropriate response status
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{user}").buildAndExpand(createdUserLobby.getUser()).toUri();
        return ResponseEntity.created(location).body(createdUserLobby);
    }

}
