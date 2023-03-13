package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class UserLobby {
    @Id
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "lobby_id")
    private Lobby lobby;

    private int pawnPosition1;

    private int pawnPosition2;

    private int pawnPosition3;

    private int pawnPosition4;


}
