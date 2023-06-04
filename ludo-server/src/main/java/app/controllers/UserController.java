package app.controllers;

import app.exceptions.PreConditionFailed;
import app.exceptions.ResourceNotFound;
import app.models.User;
import app.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UsersRepository usersRepo;

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

    @DeleteMapping(path = "/{id}")
    public User deleteUser(@PathVariable Long id){
        User user = usersRepo.deleteById(id);
        if (user==null){
            throw new ResourceNotFound("id-" + id);
        }
        return user;
    }
}
