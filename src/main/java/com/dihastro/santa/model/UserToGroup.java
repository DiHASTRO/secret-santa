package com.dihastro.santa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "USER_TO_GROUP",
    uniqueConstraints = {
        @UniqueConstraint(name = "UniqueLink", columnNames = {"group_id", "user_id"})
    })
public class UserToGroup {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    private GroupRole role;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserToGroup() {}
    public UserToGroup(Group group, User user, GroupRole role) {
        this.group = group;
        this.user = user;
        this.role = role;
    }
}
