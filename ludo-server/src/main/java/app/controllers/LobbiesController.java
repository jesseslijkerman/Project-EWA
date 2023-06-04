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

    @GetMapping(path = "/roll-dice", produces = "application/json")
    public int rollDice(){
        return this.lobbyRepo.rollDice();
    }

    @GetMapping(path = "/{id}/users", produces = "application/json")
    public List<String> getAllUsersInLobby(@PathVariable Long id){
        return this.userLobbyRepo.findAllUsersInLobby(id);
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public Lobby findById(@PathVariable Long id){
        return this.lobbyRepo.findById(id);
    }

    @GetMapping(path = "/user/{userId}", produces = "application/json")
    public List<Lobby> findByUserId(@PathVariable Long userId){
        return this.lobbyRepo.findByUserId(userId);
    }

    @GetMapping(path = "/joinable/{userId}", produces = "application/json")
    public List<Lobby> findAllJoinableLobbies(@PathVariable Long userId){
        return this.lobbyRepo.findAllJoinableLobbies(userId);
    }

    @PutMapping(path = "/{id}/start")
    public ResponseEntity<String> startLobby(@PathVariable Long id){
        Lobby lobby = lobbyRepo.findById(id);
        lobby.setStatus("ACTIVE");
        lobbyRepo.save(lobby);

        return ResponseEntity.ok("Lobby status updated successfully.");
    }


    @PostMapping(path = "")
    public ResponseEntity<Object> createLobby(@RequestBody Lobby lobby){
        Lobby createdLobby = lobbyRepo.save(lobby);

        // Return appropriate response status
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdLobby.getId()).toUri();
        return ResponseEntity.created(location).body(createdLobby);
    }

    @PostMapping(path = "/{lobbyId}/user/{userId}")
    public UserLobby addUserLobby(@PathVariable Long userId, @PathVariable Long lobbyId, @RequestBody UserLobby userLobby){
        User user = userRepo.findById(userId);
        Lobby lobby = lobbyRepo.findById(lobbyId);

        lobby.associateUser(userLobby);
        user.associateLobby(userLobby);
        userLobby.associateLobby(lobby);
        userLobby.associateUser(user);
        return this.userLobbyRepo.save(userLobby);
    }

    @PutMapping(path = "/{id}")
    public Lobby editLobby(@PathVariable Long id, @RequestBody Lobby lobby){
        if (lobby.getId() != id){
            throw new PreConditionFailed("id-" + id + " doesn't match id of body");
        }

        lobbyRepo.save(lobby);
        return lobby;
    }

    @DeleteMapping(path = "/{id}")
    public Lobby deleteLobby(@PathVariable Long id){
        Lobby lobby = lobbyRepo.deleteById(id);
        if (lobby==null){
            throw new ResourceNotFound("id-" + id);
        }
        return lobby;
    }

    @PutMapping(path = "/{id}/updateBoard/{boardUpdate}")
    public Lobby updateBoard(@PathVariable Long id, @PathVariable String boardUpdate){
        lobbyRepo.updateBoard(id, boardUpdate);
        return null;
    }

    @PutMapping(path = "/{id}/updateTurn")
    public Lobby updateTurn(@PathVariable Long id){
        lobbyRepo.updateTurn(id);
        return null;
    }

    @DeleteMapping(path = "{lobbyId}/users/{userId}")
    public ResponseEntity<String> deleteUserFromLobby(@PathVariable Long userId, @PathVariable Long lobbyId){
        Lobby lobby = lobbyRepo.findById(lobbyId);
        User user = userRepo.findById(userId);

        if (lobby == null || user == null) {
            throw new ResourceNotFound("Lobby or User not found");
        }

        userLobbyRepo.removeUserFromLobby(userId, lobbyId);

        // Check if the lobby has no more users
        List<String> remainingUsers = userLobbyRepo.findAllUsersInLobby(lobbyId);
        if (remainingUsers.isEmpty()) {
            lobbyRepo.deleteById(lobbyId);
            return ResponseEntity.ok("User removed successfully. Lobby deleted.");
        }

        return ResponseEntity.ok("User removed successfully.");
    }


}
