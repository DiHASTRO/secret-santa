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

    @Column(unique = true, nullable = false)
    private String username;

    public User() {}
    public User(String username) {
        this.username = username;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserToGroup> inGroups = new HashSet<>();
    @OneToMany(mappedBy = "toGift")
    private Set<UserToGroup> toGift = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}
