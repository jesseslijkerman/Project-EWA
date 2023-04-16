package app.compositeKeys;

import app.models.Lobby;
import app.models.User;

import java.io.Serializable;

public class UserLobbyPK implements Serializable {
    protected User user;
    protected Lobby lobby;

    public UserLobbyPK(){}

    public UserLobbyPK(User user, Lobby lobby) {
        this.user = user;
        this.lobby = lobby;
    }
}
