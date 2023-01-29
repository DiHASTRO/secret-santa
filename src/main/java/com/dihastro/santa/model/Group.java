package com.dihastro.santa.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "SANTA_GROUP")
public class Group {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String groupname;

    @Column(nullable = false)
    private Boolean isClosed;


    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserToGroup> members = new HashSet<>();

    public Group() {}
    public Group(String groupname) {
        this.groupname = groupname;
        this.isClosed = false;
    }

    public Set<UserToGroup> getMembers() {
        return new HashSet<>(members);
    }
    public Boolean getClosed() {
        return isClosed;
    }
    public void setClosed(Boolean closed) {
        isClosed = closed;
    }
    public Long getId() {
        return id;
    }
    public String getGroupname() {
        return groupname;
    }

}
