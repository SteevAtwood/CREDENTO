package com.example.application.data;

import java.util.Set;
import com.example.application.data.positionEnum.PositionEnum;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_name")
    private String username;
    private String name;

    @Enumerated(EnumType.STRING)
    private PositionEnum position;

    private String hashedPassword;
    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String name, PositionEnum position, String hashedPassword, Set<Role> roles) {
        this.username = username;
        this.name = name;
        this.position = position;
        this.hashedPassword = hashedPassword;
        this.roles = roles;

    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PositionEnum getPosition() {
        return position;
    }

    public void setPosition(PositionEnum position) {
        this.position = position;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
