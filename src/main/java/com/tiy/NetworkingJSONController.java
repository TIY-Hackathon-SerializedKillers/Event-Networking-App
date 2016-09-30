package com.tiy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

//    @RequestMapping(path = "/login.json", method = RequestMethod.POST)
//    public User login(/*email, password*/) {
//
//
//        return /*errorMessage, User*/; //make a new container class to handle this
//    }
//
//    @RequestMapping(path = "/addEvent.json", method = RequestMethod.POST)
//    public List<Event> addEvent(HttpSession session, @RequestBody Event event/*name, location, date, user*/) throws Exception{
//
//        List<Event> eventList = new ArrayList<Event>();
//        Iterable<Event> allEvents = events.findAll();
//        for (Event event : allEvents) {
//            eventList.add(event);
//        }
//        return eventList;
//
////        User user = (User)session.getAttribute("user");
////
////        if (user == null) {
////            throw new Exception("Unable to add event.");
////        }
////        event.userEvent = user;
////
////        events.save(event);
////
////        return event;
//    }
//
//    @RequestMapping(path = "/getSingleEvent.json", method = RequestMethod.POST)
//    public Event singleEventView(/*eventID*/) {
//        return event;
//    }
//
//    @RequestMapping(path = "/joinEvent.json", method = RequestMethod.POST)
//    public List<User> joinEvent(/*userId, eventId*/) {
//        return event.listOfAttendees;
//    }
//
//    @RequestMapping(path = "/requestContact.json", method = RequestMethod.POST)
//    public List<Friend> requestContact(/*userId, friendId*/) {
//        return user.listOfFriends;
//    }
//
//




}
