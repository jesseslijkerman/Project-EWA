package app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(name = "find_all_lobbies", query = "select l from Lobby l"),
        @NamedQuery(name = "find_lobbies_by_user_id", query = "SELECT l FROM Lobby l JOIN l.userLobbies ul WHERE ul.user.id = ?1"),
        @NamedQuery(name = "update_lobby_status", query = "UPDATE Lobby l SET l.status = 'ACTIVE' WHERE l.id = ?1"),
        @NamedQuery(name = "update_lobby_turn", query = "UPDATE Lobby l SET l.whoseTurn = (l.whoseTurn + 1) * (CASE WHEN l.whoseTurn < l.maxPlayers THEN 1 ELSE 0 END) + 1 * (CASE WHEN l.whoseTurn >= l.maxPlayers THEN 1 ELSE 0 END) WHERE l.id = :lobbyId"),
        @NamedQuery(name = "find_all_joinable_lobbies", query = "SELECT l FROM Lobby l WHERE l.id NOT IN (SELECT ul.lobby.id FROM UserLobby ul WHERE ul.user.id = ?1) AND l.status = 'INACTIVE'")
})

public class Lobby {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int maxPlayers;
    private int turnTimer;
    private int gameMode;
    private String name;
    private String password;
    @CreationTimestamp
    private LocalDateTime created;
    private String status;
    private int whoseTurn;
    @OneToMany(mappedBy = "lobby")
    @JsonIgnoreProperties("lobby")
    private List<UserLobby> userLobbies = new ArrayList<>();

    public Lobby(String name, String sessionId, int maxPlayers, LocalDateTime created) {
    }

    public Lobby() {

    }

    public boolean associateUser(UserLobby userLobby){
        userLobbies.add(userLobby);
        return true;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getWhoseTurn() {
        return whoseTurn;
    }

    public void setWhoseTurn(int whoseTurn) {
        this.whoseTurn = whoseTurn;
    }
}
