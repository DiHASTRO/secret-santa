package com.dihastro.santa.model;

import jakarta.annotation.Nullable;
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

    @ManyToOne
    @JoinColumn(name = "to_gift_id", nullable = true)
    private User toGift;

    @Nullable
    private String wish;

    public UserToGroup() {}
    public UserToGroup(Group group, User user, GroupRole role) {
        this.group = group;
        this.user = user;
        this.role = role;
    }

    public String getWish() {
        return wish;
    }
    public User getToGift() {
        return toGift;
    }
    public void setToGift(User toGift) {
        this.toGift = toGift;
    }
    public Group getGroup() {
        return group;
    }
    public User getUser() {
        return user;
    }
    public void setWish(String wish) {
        this.wish = wish;
    }
    public GroupRole getRole() {
        return role;
    }
    public void setRole(GroupRole role) {
        this.role = role;
    }

}
