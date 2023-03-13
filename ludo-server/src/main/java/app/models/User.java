package app.models;

import jakarta.persistence.*;

@Entity
// Order is a reserved keyword, so I'm forcing hibernate to quote an identifier
@Table(name = "`User`")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String username;
    private char countryCode;
    private String picture;

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

    public char getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(char countryCode) {
        this.countryCode = countryCode;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
