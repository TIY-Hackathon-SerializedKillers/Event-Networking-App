package com.tiy;

import javax.persistence.*;

/**
 * Created by jessicatracy on 10/2/16.
 */
@Entity
@Table(name = "notificationConnections")
public class NotificationConnection {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User user;

//    @ManyToOne
//    User friend;

    @Column(nullable = false)
    int friendId;

    public NotificationConnection() {
    }

    public NotificationConnection(User user, int userWhoWantsToBeFriendId) {
        this.user = user;
        this.friendId = userWhoWantsToBeFriendId;
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

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
}
