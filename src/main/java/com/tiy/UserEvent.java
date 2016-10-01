package com.tiy;

import javax.persistence.*;

/**
 * Created by jessicatracy on 9/29/16.
 */
@Entity
@Table(name = "user_events")
public class UserEvent {
    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    User user;

    @ManyToOne
    Event event;

    public UserEvent() {
    }

    public UserEvent(User user, Event event) {
        this.user = user;
        this.event = event;
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

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
