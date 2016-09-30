package com.tiy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jessicatracy on 9/29/16.
 */
@RestController
public class NetworkingJSONController {

    @Autowired
    UserEventRepository userEvents;

    @Autowired
    UserRepository users;

    @Autowired
    FriendRepository friends;

    @Autowired
    EventRepository events;

    @RequestMapping(path = "/login.json", method = RequestMethod.POST)
    public User login(/*email, password*/) {


        return /*errorMessage, User*/; //make a new container class to handle this
    }

    @RequestMapping(path = "/addEvent.json", method = RequestMethod.POST)
    public List<Event> addEvent(/*name, location, date, user*/) {

        List<Event> eventList = new ArrayList<Event>();
        Iterable<Event> allEvents = events.findAll();
        for (Event event : allEvents) {
            eventList.add(event);
        }
        return eventList;
    }

    @RequestMapping(path = "/getSingleEvent.json", method = RequestMethod.POST)
    public Event singleEventView(/*eventID*/) {
        return event;
    }

    @RequestMapping(path = "/joinEvent.json", method = RequestMethod.POST)
    public List<User> joinEvent(/*userId, eventId*/) {
        return event.listOfAttendees;
    }

    @RequestMapping(path = "/requestContact.json", method = RequestMethod.POST)
    public List<Friend> requestContact(/*userId, friendId*/) {
        return user.listOfFriends;
    }

    @RequestMapping(path = "/register.json", method = RequestMethod.POST)
    public User register(/*@RequestBody User user email, firstName, lastName, password, techSkills*/) {

        return errorMessage, User;
    }




}
