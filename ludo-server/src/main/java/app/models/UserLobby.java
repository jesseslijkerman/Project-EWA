package app.models;

import app.compositeKeys.UserLobbyPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

@Entity
@IdClass(UserLobbyPK.class)
@NamedQuery(name = "find_all_user_lobbies", query = "select ul from UserLobby ul")
@NamedQuery(name = "get_player_number", query = "SELECT ul.playerNumber FROM UserLobby ul JOIN ul.lobby lobby WHERE lobby.id = :lobbyId AND ul.user.id = :userId")
@NamedQuery(name="find_user_lobby", query = "select ul from UserLobby ul WHERE ul.lobby.id = ?1")
@NamedQuery(name = "find_users_in_lobby", query = "select ul.user.username from UserLobby ul where ul.lobby.id= ?1 ORDER BY ul.playerNumber ASC")
@NamedQuery(name = "remove_user_from_lobby", query = "DELETE FROM UserLobby ul WHERE ul.user.id IN (SELECT u.id FROM User u WHERE u.id = :userId) AND ul.lobby.id = :lobbyId")
@NamedQuery(name = "get_userId_turn", query = "SELECT ul.user.id FROM Lobby l JOIN UserLobby ul ON l.id = ul.lobby.id WHERE l.id = :lobbyId AND ul.playerNumber = l.whoseTurn")
@NamedQuery(name = "get_current_turn", query = "SELECT l.whoseTurn FROM Lobby l WHERE l.id = :lobbyId")
public class UserLobby {
    @Id
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Id
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "lobby_id")
    @JsonIgnore
    private Lobby lobby;

    private String color;
    private int playerNumber;

    public UserLobby() {
    }

    public boolean associateLobby(Lobby lobby){
        this.lobby = lobby;
        return true;
    }

    public boolean associateUser(User user){
        this.user = user;
        return true;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
