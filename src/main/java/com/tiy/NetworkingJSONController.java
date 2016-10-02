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

    @RequestMapping(path = "/viewUsers.json", method = RequestMethod.GET)
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
                    System.out.println("New login from: " + thisUser.getFirstName() + " " + thisUser.getLastName());
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
            //set the event's list of attendees before adding
            event.setAttendees(setListOfAttendees(event));
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
            setListOfAttendees(currentEvent);
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

    public ArrayList<User> setListOfAttendees(Event event) {
        //I need to query the userevents for ones that have this eventid
        Iterable<UserEvent> allUserEventsForThisEvent = userEvents.findAllByEventId(event.getId());
        ArrayList<User> allAttendees = new ArrayList<>();
        for (UserEvent currentUserEvent : allUserEventsForThisEvent) {
            //get the user for currentUserEvent
            User currentUser = users.findOne(currentUserEvent.getUser().getId());
            //add them to the list of people attending the event
            allAttendees.add(currentUser);
        }
        return allAttendees;
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
    public ArrayList<User> joinEvent(@RequestBody UserIDEventIDConnectionContainer idContainer) {
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

    // What we need from Dan: container (FriendConnectionContainer object) holding userId and friendID
    // PROBLEM: RIGHT NOW THIS IS NOT ASKING THE USER TO GRANT PERMISSION. WILL NEED 2 ENDPOINTS I THINK.
    // This one is just adding the friend connection in friend database and returning the user's friends, so
    // it's assuming that permission was already granted.
    @RequestMapping(path = "/requestContact.json", method = RequestMethod.POST)
    public ArrayList<Friend> requestContact(@RequestBody FriendConnectionContainer friendConnectionContainer) throws Exception {
        //Find USER in users based on userID -- just to make sure valid
        User user = users.findOne(friendConnectionContainer.getUserId());
//        User user = friends.findOne(friendConnectionContainer.getUserId());

        //Find FRIEND in users based on userID -- just to make sure valid
        User friend = users.findOne(friendConnectionContainer.getUserWhoWantsToBeFriendId());
        if (user == null) {
            throw new Exception("Requested user is not in database");
        } else if (friend == null) {
            throw new Exception("Requested friend is not in database");
        } else {
            //Make a new Friend object with userId from db and friendId from db
            Friend myFriend = new Friend(user, friendConnectionContainer.getUserWhoWantsToBeFriendId());
            //save to friends table
            friends.save(myFriend);
            //return the user's list of friends by querying table
            Iterable<Friend> allMyFriends = friends.findAllByUserId(user.getId());
            ArrayList<Friend> listOfMyFriends = new ArrayList<>();
            for (Friend currentFriend : allMyFriends) {
                listOfMyFriends.add(currentFriend);
            }
            return listOfMyFriends;
        }
    }

    //New idea: Make a viewUserInfo method that checks if the currentuser is on the friends list of the person they want to see the
    //info of. If currentuser on list, return contact info of friend (return the friend's whole user object).
    // If currentuser not on list, return error message.
    //On Dan's side, he will display the email if he gets the user back, and display the button to request contact info if
    //he gets the error message back.
    // What we need from Dan: container holding int userId (current user, requester), int friendId (person currentUser wants to email, requestee)
    @RequestMapping(path = "/viewUserInfo.json", method = RequestMethod.POST)
    public LoginContainer viewUserInfo(@RequestBody FriendConnectionContainer friendConnectionContainer) {
        //go through friends table and find
        // current user is seeing if they are on friend list of friend
        User requesterUser = users.findOne(friendConnectionContainer.userId);
        User requesteeFriend = users.findOne(friendConnectionContainer.userWhoWantsToBeFriendId);

        LoginContainer myContainer = new LoginContainer();
        boolean noAccess = true;

        Iterable<Friend> requesteesFriendList = friends.findAllByUserId(friendConnectionContainer.userWhoWantsToBeFriendId);
        for (Friend friend : requesteesFriendList) {
            if (friendConnectionContainer.userId == friend.getId()) {
                myContainer.user = users.findOne(friendConnectionContainer.userWhoWantsToBeFriendId);
                myContainer.errorMessage = null;
                noAccess = false;
            }
        }

        if (noAccess) {
            myContainer.user = null;
            myContainer.errorMessage = "You do not have permission to view this person's contact info.";
        }

        return myContainer;
    }

//    @RequestMapping(path = "/viewFriends.json", method = RequestMethod.GET)
//    public ArrayList<Friend> viewFriends(@RequestBody FriendConnectionContainer friendConnectionContainer) throws Exception {
//        //Find USER in users based on userID -- just to make sure valid
//        User user = users.findOne(friendConnectionContainer.getUserId());
//        //Find FRIEND in users based on userID -- just to make sure valid
//        User friend = users.findOne(friendConnectionContainer.getFriendId());
//        if (user == null) {
//            throw new Exception("Requested user is not in database");
//        } else if (friend == null) {
//            throw new Exception("Requested friend is not in database");
//        } else {
//            //Make a new Friend object with userId from db and friendId from db
//            Friend myFriend = new Friend(user);
//            //save to friends table
//            friends.save(myFriend);
//            //return the user's list of friends by querying table
//            Iterable<Friend> allMyFriends = friends.findAllByUserId(user.getId());
//            ArrayList<Friend> listOfMyFriends = new ArrayList<>();
//            for (Friend currentFriend : allMyFriends) {
//                listOfMyFriends.add(currentFriend);
//            }
//            return listOfMyFriends;
//        }
//    }

    // Just for us to see what's in friends table
    @RequestMapping(path = "/viewFriends.json", method = RequestMethod.GET)
    public ArrayList<Friend> viewFriends() {
        Iterable<Friend> listOfFriends = friends.findAll();
        ArrayList<Friend> allFriends = new ArrayList<>();
        for (Friend friend : listOfFriends) {
            allFriends.add(friend);
        }
        return allFriends;
    }

    //When user gets a notification, and wants to give the other person their email, go to this endpoint.
    //It will add the other person to their list of friends.
    //It will just give Dan back a message that says successful or not.
    //What we need from Dan: container holding int userId, int userWhoWantsToBeFriendId
    @RequestMapping(path = "/addToMyFriendList.json")
    public String addToMyFriendList(@RequestBody FriendConnectionContainer friendConnectionContainer) {
        int userId = friendConnectionContainer.getUserId();
        int userWhoWantsToBeFriendId = friendConnectionContainer.getUserWhoWantsToBeFriendId();

        Friend newFriend = new Friend(users.findOne(userId), userWhoWantsToBeFriendId);

        return "User added to your friend list!";
    }






}
