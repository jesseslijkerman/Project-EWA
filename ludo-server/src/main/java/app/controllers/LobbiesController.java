package app.controllers;

import app.exceptions.PreConditionFailed;
import app.exceptions.ResourceNotFound;
import app.models.Lobby;
import app.models.User;
import app.models.UserLobby;
import app.repositories.LobbiesRepository;
import app.repositories.UserLobbyRepository;
import app.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/lobbies")
public class LobbiesController {
    @Autowired
    private LobbiesRepository lobbyRepo;

    @Autowired
    private UsersRepository userRepo;

    @Autowired
    private UserLobbyRepository userLobbyRepo;

    @GetMapping(path = "", produces = "application/json")
    public List<Lobby> getAllLobbys(){
        return this.lobbyRepo.findAll();
    }

    @GetMapping(path = "/{id}/users", produces = "application/json")
    public List<UserLobby> getAllUsersInLobby(@PathVariable int id){
        return this.userLobbyRepo.findAllUsersInLobby(id);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Lobby findById(@PathVariable int id){
        Lobby lobby = this.lobbyRepo.findById(id);
        if (lobby==null){
            throw new ResourceNotFound("id-" + id);
        }
        return lobby;
    }

    @GetMapping(path = "/user/{userId}", produces = "application/json")
    public List<Lobby> findByUserId(@PathVariable int userId){
        return this.lobbyRepo.findByUserId(userId);
    }

    @GetMapping(path = "/joinable/{userId}", produces = "application/json")
    public List<Lobby> findAllJoinableLobbies(@PathVariable int userId){
        return this.lobbyRepo.findAllJoinableLobbies(userId);
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> createLobby(@RequestBody Lobby lobby){
        Lobby createdLobby = lobbyRepo.save(lobby);

        // Return appropriate response status
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdLobby.getId()).toUri();
        return ResponseEntity.created(location).body(createdLobby);
    }

    @PostMapping(path = "/{lobbyId}/user/{userId}")
    public UserLobby addUserLobby(@PathVariable int userId, @PathVariable int lobbyId, @RequestBody UserLobby userLobby){
        User user = userRepo.findById(userId);
        Lobby lobby = lobbyRepo.findById(lobbyId);

        lobby.associateUser(userLobby);
        user.associateLobby(userLobby);
        userLobby.associateLobby(lobby);
        userLobby.associateUser(user);
        return this.userLobbyRepo.save(userLobby);
    }

    @PutMapping(path = "/{id}")
    public Lobby editLobby(@PathVariable int id, @RequestBody Lobby lobby){
        if (lobby.getId() != id){
            throw new PreConditionFailed("id-" + id + " doesn't match id of body");
        }

        lobbyRepo.save(lobby);
        return lobby;
    }

    @DeleteMapping(path = "/{id}")
    public Lobby deleteLobby(@PathVariable int id){
        Lobby lobby = lobbyRepo.deleteById(id);
        if (lobby==null){
            throw new ResourceNotFound("id-" + id);
        }
        return lobby;
    }

    @DeleteMapping(path = "{id}/users/{username}")
    public Lobby deleteUserFromLobby(@PathVariable String username, @PathVariable int id){
        userLobbyRepo.removeUserFromLobby(username, id);
        return null;
    }


}
