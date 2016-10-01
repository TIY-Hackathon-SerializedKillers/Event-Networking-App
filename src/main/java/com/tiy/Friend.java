package com.tiy;

import javax.persistence.*;

/**
 * Created by jessicatracy on 9/29/16.
 */
@Entity
@Table(name = "friends")
public class Friend extends User {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User user;

    @ManyToOne
    User friend;

    public Friend() {
    }

    public Friend(User user) {
        this.user = user;
        this.friend = this;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getFriend() {
        return friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }
}
