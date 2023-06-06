package app.models;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
// User is a reserved keyword, so I'm forcing hibernate to quote an identifier
@Table(name = "`User`")
@NamedQueries({
        @NamedQuery(name = "find_all_users", query = "select u from User u"),
        @NamedQuery(name = "findUserByEmail", query = ("select user from User user where user.email = :emailParam")),
        @NamedQuery(name = "reset_password", query = ("UPDATE User SET password = :newPassword WHERE id = :userId")),
        @NamedQuery(name = "find_friends_by_user_id", query = "select u.friends from User u where u.id = :userId")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String username;
    private String countryCode;
    private String picture;
    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<UserLobby> userLobbies = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "UserFriends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<User> friends = new ArrayList<>();

    public User() {
    }

    public boolean associateLobby(UserLobby userLobby){
        userLobbies.add(userLobby);
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public List<UserLobby> getUserLobbies() {
        return userLobbies;
    }

    public void addUserLobby(UserLobby userLobby) {
        this.userLobbies.add(userLobby);
    }

    @JsonIgnore
    public List<User> getFriends() {
        return friends;
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public void removeFriend(User friend) {
        this.friends.remove(friend);
    }
}
