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

    // What we need from Dan: container holding user info: String email, String password, String firstName, String lastName, String techSkills
    @RequestMapping(path = "/register.json", method = RequestMethod.POST)
    //Problem -> can't call @RequestBody on multiple things! Just a single java object.
    public LoginContainer register(@RequestBody User newUser) {
        users.save(newUser);

        User retrievedUser = users.findOne(newUser.getId());
        LoginContainer myLoginContainer;
        if (retrievedUser == null) {
            myLoginContainer = new LoginContainer("Could not create user", null);
        } else {
            myLoginContainer = new LoginContainer(null, retrievedUser);
        }

        return myLoginContainer;
    }

    @RequestMapping(path = "/viewusers.json", method = RequestMethod.GET)
    //Problem -> can't call @RequestBody on multiple things! Just a single java object.
    public List<User> getUsers() {

        List<User> userList = new ArrayList<>();
        Iterable <User> allUsers = users.findAll();
        for (User user : allUsers) {
            userList.add(user);
        }
        return userList;
    }

    // What we need from Dan: container holding String email and String password
    @RequestMapping(path = "/login.json", method = RequestMethod.POST)
    public LoginContainer login(@RequestBody User user /*String email, String password*/) throws Exception {

        LoginContainer myLoginContainer = new LoginContainer();

        if (user.email == null) {
            throw new Exception("The information entered was incorrect");
        } else if (user.email != null) {
            User thisUser = users.findByEmail(user.email);
            if (thisUser == null) {
                myLoginContainer.setErrorMessage("User does not have account");
                myLoginContainer.setUser(null);
            } else {
                if (!user.password.equals(thisUser.password)) {
                    myLoginContainer.setErrorMessage("Password does not match");
                    myLoginContainer.setUser(null);
                } else {
                    myLoginContainer.setErrorMessage(null);
                    myLoginContainer.setUser(thisUser);
                }
            }
        }
        return myLoginContainer; //make a new container class to handle this
    }

    // What we need from Dan: nothing!
    @RequestMapping(path = "/getAllEvents.json", method = RequestMethod.GET)
    public ArrayList<Event> getAllEvents() {
        Iterable<Event> listOfEvents = events.findAll();
        ArrayList<Event> allEvents = new ArrayList<Event>();
        for (Event event : listOfEvents) {
            allEvents.add(event);
        }
        return allEvents;
    }

// This is where we stopped on Friday evening.

    // What we need from Dan: container holding event info: String name, String location, String date, String time (will have an empty list of attendees when first created)
    @RequestMapping(path = "/addEvent.json", method = RequestMethod.POST)
    public List<Event> addEvent(@RequestBody Event event) throws Exception{
        events.save(event);

        List<Event> eventList = new ArrayList<Event>();
        Iterable<Event> allEvents = events.findAll();
        for (Event currentEvent : allEvents) {
            eventList.add(currentEvent);
        }
        return eventList;

//        User user = (User)session.getAttribute("user");
//
//        if (user == null) {
//            throw new Exception("Unable to add event.");
//        }
//        event.userEvent = user;
//
//        events.save(event);
//
//        return event;
    }

    // What we need from Dan: Just int eventId
    @RequestMapping(path = "/getSingleEvent.json", method = RequestMethod.POST)
    public Event singleEventView(@RequestBody int eventID) {
        Event event = events.findOne(eventID);
        System.out.println("Now returning event " + event.getName());
        return event;
    }

    // What we need from Dan: container holding int userId and int eventId
    @RequestMapping(path = "/joinEvent.json", method = RequestMethod.POST)
    public ArrayList<User> joinEvent(@RequestBody IDContainer idContainer) {
        //use userId to go get userJoiningEvent
        User userJoiningEvent = users.findOne(idContainer.getUserId());
        //use eventId to go get eventBeingJoined
        Event eventBeingJoined = events.findOne(idContainer.getEventId());

        //Save user and event connection to userEvents table in db
        UserEvent userEvent = new UserEvent(userJoiningEvent, eventBeingJoined);
        userEvents.save(userEvent);

        //add userJoiningEvent to eventBeingJoined's list of attendees
        //it says attendees is null??
//        eventBeingJoined.addToAttendees(userJoiningEvent);
        //Instead, try finding all attendees by userEvents table
        ArrayList<User> thisEventsAttendees = new ArrayList<>();
        //If it doesn't work, might need to pass in the event instead of the event id.
        Iterable<UserEvent> allUserEventsLinkedToThisEvent = userEvents.findAllByEventId(idContainer.getEventId());
        for (UserEvent currentUserEvent : allUserEventsLinkedToThisEvent) {
            thisEventsAttendees.add(currentUserEvent.getUser());
        }

        System.out.println("Adding user " + userJoiningEvent.getFirstName() + " " + userJoiningEvent.getLastName() + " to event " + eventBeingJoined.getName());

        //resave eventBeingJoined with updated list of attendees
        //now won't need to do this bc just linking with userEvents table instead of array list in event class
//        events.save(eventBeingJoined);

        //return the event's list of attendees
//        return eventBeingJoined.getAttendees();
        return thisEventsAttendees;
    }

    //Just for local testing purposes
    @RequestMapping(path = "/seeUserEvents.json", method = RequestMethod.GET)
    public ArrayList<UserEvent> seeUserEvents() {
        ArrayList<UserEvent> listOfUserEvents = new ArrayList<>();
        Iterable<UserEvent> allUserEvents = userEvents.findAll();
        for (UserEvent currentUserEvent : allUserEvents) {
            listOfUserEvents.add(currentUserEvent);
        }
        return listOfUserEvents;
    }


//    @RequestMapping(path = "/requestContact.json", method = RequestMethod.POST)
//    public List<Friend> requestContact(/*userId, friendId*/) {
//        return user.listOfFriends;
//    }
//
//




}
