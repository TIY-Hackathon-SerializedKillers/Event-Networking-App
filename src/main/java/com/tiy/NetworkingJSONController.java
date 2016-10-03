package com.tiy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
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

    @Autowired
    NotificationConnectionRepository notificationConnections;

    // What we need from Dan: container holding user info: String email, String password, String firstName, String lastName, String techSkills
    @RequestMapping(path = "/register.json", method = RequestMethod.POST)
    //Problem -> can't call @RequestBody on multiple things! Just a single java object.
    public LoginContainer register(@RequestBody User newUser) {
        users.save(newUser);

        User retrievedUser = users.findOne(newUser.getId());
//        HashMap<User, String> myHashMap = new HashMap<>();
//        retrievedUser.setListOfPeopleAndStatusForSeeingMyStuff(myHashMap);
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
    @RequestMapping(path = "/showAllEvents.json", method = RequestMethod.GET)
    public ArrayList<Event> showAllEvents() {
        Iterable<Event> listOfEvents = events.findAll();
        ArrayList<Event> allEvents = new ArrayList<Event>();
        for (Event event : listOfEvents) {
            //set the event's list of attendees before adding
            event.setAttendees(setListOfAttendees(event));
            allEvents.add(event);
        }
        return allEvents;
    }

    @RequestMapping(path = "/getAllEvents.json", method = RequestMethod.POST)
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
//    @RequestMapping(path = "/requestContact.json", method = RequestMethod.POST)
//    public ArrayList<Friend> requestContact(@RequestBody FriendConnectionContainer friendConnectionContainer) throws Exception {
//        //Find USER in users based on userID -- just to make sure valid
//        User user = users.findOne(friendConnectionContainer.getUserId());
////        User user = friends.findOne(friendConnectionContainer.getUserId());
//
//        //Find FRIEND in users based on userID -- just to make sure valid
//        User friend = users.findOne(friendConnectionContainer.getUserWhoWantsToBeFriendId());
//        if (user == null) {
//            throw new Exception("Requested user is not in database");
//        } else if (friend == null) {
//            throw new Exception("Requested friend is not in database");
//        } else {
//            //Make a new Friend object with userId from db and friendId from db
//            Friend myFriend = new Friend(user, friendConnectionContainer.getUserWhoWantsToBeFriendId());
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



    //This is the initial method where on Dan's side users will click on an attendee and see one of two things:
    // if they are already on the person's friend list, they will see the person's email.
    // if they are not on the person's friend list, a button will pop up to ask if they would like to request access.
    // This is the method that checks if they are on the friend list or not.
    // Returns an error message if not on friend list, returns the user's info if they are.
    // what we need from Dan: container holding userId (one whose friend list we're checking) and friendId (one who wants to get the other person's email)
    @RequestMapping(path = "/viewUserInfo.json", method = RequestMethod.POST)
    public LoginContainer viewUserInfo(@RequestBody FriendConnectionContainer friendConnectionContainer) {
        int userId = friendConnectionContainer.getUserId();
        int userWhoWantsToBeFriendId = friendConnectionContainer.getUserWhoWantsToBeFriendId();

        boolean onFriendList = false;
        //check the user's friend list by going to friend table and querying by userId.
        Iterable<Friend> allOfUsersFriends = friends.findAllByUserId(userId);
        for (Friend friend : allOfUsersFriends) {
            if (friend.friendId == userWhoWantsToBeFriendId) {
                onFriendList = true;
            }
        }
        LoginContainer loginContainer = new LoginContainer();
        User userToReturn;
        if (onFriendList) {
            //return container with the ORIGINAL users contact info (not user who wants to be friend) and null error message
            loginContainer.setErrorMessage(null);
            userToReturn = users.findOne(userId);
            loginContainer.setUser(userToReturn);
        } else {
            //return container with error message and null user
            loginContainer.setErrorMessage("Cannot access this person's email because you are not on their friend list.");
            loginContainer.setUser(null);
        }
        return loginContainer;
    }

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

    // When the user can't see the other person's contact info and button pops up to request contact, button will
    // send them here.
    // This will return a RequestContactContainer item that gives the user who the notification needs to go to
    // and an updated arrayList of users who would like that person's contact info.
    // What we need from Dan: FriendConnectionContainer with int userId and int userWhoWantsToBeFriendId
    @RequestMapping(path = "/requestContact.json", method = RequestMethod.POST)
    public RequestContactContainer requestContact(@RequestBody FriendConnectionContainer friendConnectionContainer) {
//    public User requestContact(@RequestBody FriendConnectionContainer friendConnectionContainer) {
        int userId = friendConnectionContainer.getUserId();
        int userWhoWantsToBeFriendId = friendConnectionContainer.getUserWhoWantsToBeFriendId();

        User userWhoGetsNotification = users.findOne(userId);
        NotificationConnection notificationConnection = new NotificationConnection(userWhoGetsNotification, userWhoWantsToBeFriendId);
        notificationConnections.save(notificationConnection);

        ArrayList<User> usersWhoWantYourContactInfo = new ArrayList<>();
        Iterable<NotificationConnection> allNotificationConnectionsForUser = notificationConnections.findAllByUserId(userId);
        for (NotificationConnection currentConnection : allNotificationConnectionsForUser) {
            int currentFriendId = currentConnection.getFriendId();
            User currentFriendUser = users.findOne(currentFriendId);
            usersWhoWantYourContactInfo.add(currentFriendUser);
        }

//        ArrayList<User> usersWhoWantYourContactInfo = new ArrayList<>();
//        HashMap<User, String> userHashMap = new HashMap<>();
//        Iterable<NotificationConnection> allNotificationConnectionsForUser = notificationConnections.findAllByUserId(userId);
//        for (NotificationConnection currentNotificationConnection : allNotificationConnectionsForUser) {
//            int currentFriendId = currentNotificationConnection.getFriendId();
//            User currentFriendUser = users.findOne(currentFriendId);
//            userHashMap.put(currentFriendUser, "pending");
//        }

//        userWhoGetsNotification.setListOfPeopleAndStatusForSeeingMyStuff(userHashMap);

        RequestContactContainer myReturnContainer = new RequestContactContainer(userWhoGetsNotification, usersWhoWantYourContactInfo);

//        return userWhoGetsNotification;
        return myReturnContainer;
    }

    //When user gets a notification, and wants to give the other person their email, go to this endpoint.
    //It will add the other person to their list of friends.
    //It will give back the friend user object
    //What we need from Dan: container holding int userId, int userWhoWantsToBeFriendId
    @RequestMapping(path = "/addToMyFriendList.json")
    public User addToMyFriendList(@RequestBody FriendConnectionContainer friendConnectionContainer) {
        int userId = friendConnectionContainer.getUserId();
        int userWhoWantsToBeFriendId = friendConnectionContainer.getUserWhoWantsToBeFriendId();

        User currentUser = users.findOne(userId);
        User friendUser = users.findOne(userWhoWantsToBeFriendId);
        Friend newFriend;

        if (friendUser != null) {
            newFriend = new Friend(users.findOne(userId), userWhoWantsToBeFriendId);
            friends.save(newFriend);
            System.out.println("User added to your friend list: " + friendUser.getFirstName());
            System.out.println("New friend in database with id: " + newFriend.getId());

//            updateUsersListOfPeopleWhoCanSeeMyStuff(currentUser);

            //delete from notificationconnection table since connection has been made
            NotificationConnection thisNotificationConnection = notificationConnections.findByUserIdAndFriendId(userId, userWhoWantsToBeFriendId);
            notificationConnections.delete(thisNotificationConnection.getId());
        }

        return friendUser;
    }

    // Just for us to see what's in notification connection table
    @RequestMapping(path = "/viewNotificationConnections.json", method = RequestMethod.GET)
    public ArrayList<NotificationConnection> viewNotificationConnections() {
        Iterable<NotificationConnection> listOfNotificationConnections = notificationConnections.findAll();
        ArrayList<NotificationConnection> allNotificationConnections = new ArrayList<>();
        for (NotificationConnection currentNotificationConnection : listOfNotificationConnections) {
            allNotificationConnections.add(currentNotificationConnection);
        }
        return allNotificationConnections;
    }


    @RequestMapping(path = "/returnFriends.json", method = RequestMethod.POST)
    public ArrayList<Friend> returnFriends() {
        Iterable<Friend> listOfFriends = friends.findAll();
        ArrayList<Friend> allFriends = new ArrayList<>();
        for (Friend friend : listOfFriends) {
            allFriends.add(friend);
        }
        return allFriends;
    }

//    public void updateUsersListOfPeopleWhoCanSeeMyStuff(User currentUser) {
//        Iterable<Friend> allFriendsByCurrentUser = friends.findAllByUserId(currentUser.getId());
//        ArrayList<User> allUsersFriends = new ArrayList<>();
//        for (Friend currentFriend : allFriendsByCurrentUser) {
//            int currentFriendId = currentFriend.getFriendId();
//            User currentFriendUser = users.findOne(currentFriendId);
//            allUsersFriends.add(currentFriendUser);
//        }
//        currentUser.setListOfPeopleWhoCanSeeMyStuff(allUsersFriends);
//    }








}
