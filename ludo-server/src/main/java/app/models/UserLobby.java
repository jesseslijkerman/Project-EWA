package app.models;

import app.compositeKeys.UserLobbyPK;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@IdClass(UserLobbyPK.class)
@NamedQuery(name = "find_all_user_lobbies", query = "select ul from UserLobby ul")
@NamedQuery(name = "find_users_in_lobby", query = "select ul.user.username from UserLobby ul where ul.lobby.id= ?1")
@NamedQuery(name = "remove_user_from_lobby", query = "DELETE FROM UserLobby ul WHERE ul.user.id IN (SELECT u.id FROM User u WHERE u.username = :username) AND ul.lobby.id = :lobbyId")
public class UserLobby {
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "lobby_id")
    @JsonIgnore
    private Lobby lobby;

    private int pawnPosition1;

    private int pawnPosition2;

    private int pawnPosition3;

    private int pawnPosition4;

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

    public int getPawnPosition1() {
        return pawnPosition1;
    }

    public void setPawnPosition1(int pawnPosition1) {
        this.pawnPosition1 = pawnPosition1;
    }

    public int getPawnPosition2() {
        return pawnPosition2;
    }

    public void setPawnPosition2(int pawnPosition2) {
        this.pawnPosition2 = pawnPosition2;
    }

    public int getPawnPosition3() {
        return pawnPosition3;
    }

    public void setPawnPosition3(int pawnPosition3) {
        this.pawnPosition3 = pawnPosition3;
    }

    public int getPawnPosition4() {
        return pawnPosition4;
    }

    public void setPawnPosition4(int pawnPosition4) {
        this.pawnPosition4 = pawnPosition4;
    }
}
