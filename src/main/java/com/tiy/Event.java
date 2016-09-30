package com.tiy;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by jessicatracy on 9/29/16.
 */
@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String location;

    @Column(nullable = false)
    String date;

    @Column(nullable = false)
    String time;

    @Column
    ArrayList<User> attendees;

    @ManyToOne
    UserEvent userEvent;

    public Event() {
    }

    public Event(String name, String location, String date, String time) {
        this.name = name;
        this.location = location;
        this.date = date;
        this.time = time;
    }

    public void addToAttendees(User user) {
        //But I will need to resave whole event to db as well in json controller. (check)
        attendees.add(user);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ArrayList<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(ArrayList<User> attendees) {
        this.attendees = attendees;
    }

    public UserEvent getUserEvent() {
        return userEvent;
    }

    public void setUserEvent(UserEvent userEvent) {
        this.userEvent = userEvent;
    }
}
