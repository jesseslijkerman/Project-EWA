package app.controllers;

import app.exceptions.PreConditionFailed;
import app.exceptions.ResourceNotFound;
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
    public List<UserLobby> getAllUserLobbys(){
        return this.userLobbyRepo.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public List<UserLobby> findById(@PathVariable Long id){
        return this.userLobbyRepo.findById(id);
    }

    @GetMapping(path = "/{id}/turn", produces = "application/json")
    public Long getTurn(@PathVariable Long id){
        return this.userLobbyRepo.getTurn(id);
    }

    @GetMapping(path = "/{id}/{userId}/player_number", produces = "application/json")
    public Integer getPlayerNumber(@PathVariable Long id, @PathVariable Long userId){
        return this.userLobbyRepo.getPlayerNumber(id, userId);
    }

    @GetMapping(path = "/{id}/current_turn", produces = "application/json")
    public Integer getCurrentTurn(@PathVariable Long id){
        return this.userLobbyRepo.getCurrentTurn(id);
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> createLobby(@RequestBody UserLobby userLobby){
        UserLobby createdLobby = userLobbyRepo.save(userLobby);

        // Return appropriate response status
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdLobby.getLobby().getId()).toUri();
        return ResponseEntity.created(location).body(createdLobby);
    }

    @PutMapping(path = "/{id}")
    public UserLobby editLobby(@PathVariable Long id, @RequestBody UserLobby userLobby){
        if (userLobby.getLobby().getId() != id){
            throw new PreConditionFailed("id-" + id + " doesn't match id of body");
        }

        userLobbyRepo.save(userLobby);
        return userLobby;
    }

    @DeleteMapping(path = "/{id}")
    public UserLobby deleteLobby(@PathVariable Long id){
        UserLobby userLobby = userLobbyRepo.deleteById(id);
        if (userLobby==null){
            throw new ResourceNotFound("id-" + id);
        }
        return userLobby;
    }
}
