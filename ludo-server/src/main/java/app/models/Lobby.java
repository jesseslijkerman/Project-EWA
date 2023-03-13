package app.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lobby {
    @Id
    private Long id;
    private int maxPlayers;
    private int turnTimer;
    private int gameMode;
    private String name;
    private String password;
    @CreationTimestamp
    private LocalDateTime created;
    @OneToMany(mappedBy = "lobby")
    @JsonManagedReference
    private List<UserLobby> userLobbies = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getTurnTimer() {
        return turnTimer;
    }

    public void setTurnTimer(int turnTimer) {
        this.turnTimer = turnTimer;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public List<UserLobby> getUserLobbies() {
        return userLobbies;
    }

    public void setUserLobbies(UserLobby userLobby) {
        this.userLobbies.add(userLobby);
    }
}
