package com.dihastro.santa.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    public User() {}
    public User(String username) {
        this.username = username;
    }

    @OneToMany(mappedBy = "user")
    Set<UserToGroup> inGroups = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
