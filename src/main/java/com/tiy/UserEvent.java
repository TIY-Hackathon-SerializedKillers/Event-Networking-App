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

//    @ManyToOne
//    User user;

//    @ManyToOne
//    Event event;

}
