package com.tiy;

import javax.persistence.*;

/**
 * Created by jessicatracy on 9/29/16.
 */
@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User user;

    public Friend() {
    }

    public Friend(User user) {
        this.user = user;
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
}
