package app.controllers;

import app.exceptions.PreConditionFailed;
import app.exceptions.ResourceNotFound;
import app.models.User;
import app.repositories.UsersRepository;
import app.services.EmailService;
import app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.TypedQuery;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UsersRepository usersRepo;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;


    @GetMapping(path = "", produces = "application/json")
    public List<User> getAllUsers(){
        return this.usersRepo.findAll();
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public User findById(@PathVariable Long id){
        User user = this.usersRepo.findById(id);
        if (user==null){
            throw new ResourceNotFound("id-" + id);
        }
        return user;
    }

    @GetMapping(path = "/nameOrEmail/{param}", produces = "application/json")
    public User findByNameOrEmail(@PathVariable String param){
        User user = this.usersRepo.findByEmail(param);
        if (user==null){
            throw new ResourceNotFound("param-" + param);
        }
        return user;
    }

    @PostMapping(path = "")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        User createdUser = usersRepo.signup(user);

        // Return appropriate response status
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
        return ResponseEntity.created(location).body(createdUser);
    }

    @PutMapping(path = "/{id}")
    public User editUser(@PathVariable Long id, @RequestBody User user){
        if (user.getId() != id){
            throw new PreConditionFailed("id-" + id + " doesn't match id of body");
        }

        usersRepo.save(user);
        return user;

    }

    @PutMapping(path = "/changePassword/{id}/{newPassword}")
    public User changePassword(@PathVariable Long id, @PathVariable String newPassword) {
        return usersRepo.updatePassword(id, newPassword);
    }


    @PutMapping(path = "/resetPassword/{token}/{newPassword}")
    public User changePasswordWithToken(@PathVariable String token, @PathVariable String newPassword) {
        User user = usersRepo.findIdByToken(token);
        return usersRepo.resetPassword(user.getId(), newPassword);
    }

    @PostMapping(path = "/ForgotPassword/{email}", produces = "application/json")
    public ResponseEntity<String> processForgotPassword(@PathVariable String email){
        User user = usersRepo.findByEmail(email);
        if (user != null){
            userService.generatePasswordResetToken(email);
            return ResponseEntity.ok("Password reset email sent.");
        } else {
            return ResponseEntity.ok("There is no account with this email");
        }
    }

    @PostMapping(path = "/{userId}/invite/{friendId}/{matchId}")
    public ResponseEntity<String> inviteToGame(@PathVariable Long userId, @PathVariable Long friendId, @PathVariable Long matchId){
        User user = usersRepo.findById(userId);
        User friend = usersRepo.findById(friendId);

        if (user == null){
            throw new ResourceNotFound("User with id " + userId + " not found");
        } else if (friend == null){
            throw new ResourceNotFound("User with id " + friendId + " not found");
        }

        usersRepo.inviteToGame(friend, user, matchId);
        return ResponseEntity.ok("Invite email sent");
    }

    @GetMapping

    @DeleteMapping(path = "/{id}")
    public User deleteUser(@PathVariable Long id){
        User user = usersRepo.deleteById(id);
        if (user==null){
            throw new ResourceNotFound("id-" + id);
        }
        return user;
    }

    @PutMapping(path = "/{userId}/friend/{friendId}")
    public User addFriend(@PathVariable Long userId, @PathVariable Long friendId){
        User user = usersRepo.findById(userId);
        User friend = usersRepo.findById(friendId);

        if (user == null){
            throw new ResourceNotFound("User with id " + userId + " not found");
        } else if (friend == null){
            throw new ResourceNotFound("User with id " + friendId + " not found");
        }

        return usersRepo.addFriend(user, friend);
    }

    @DeleteMapping(path = "/{userId}/friend/{friendId}")
    public User removeFriend(@PathVariable Long userId, @PathVariable Long friendId){
        User user = usersRepo.findById(userId);
        User friend = usersRepo.findById(friendId);

        if (user == null){
            throw new ResourceNotFound("User with id " + userId + " not found");
        } else if (friend == null){
            throw new ResourceNotFound("User with id " + friendId + " not found");
        }

        return usersRepo.removeFriend(user, friend);
    }

    @GetMapping(path = "/friends/{id}")
    public List<User> findFriendsByUserId(@PathVariable Long id) {
        return this.usersRepo.findFriendsByUserId(id);
    }
}
