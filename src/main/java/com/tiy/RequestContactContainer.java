package com.tiy;

import java.util.ArrayList;

/**
 * Created by jessicatracy on 10/2/16.
 */
public class RequestContactContainer {
    User userGettingNotification;
    ArrayList<User> usersRequestingContact;

    public RequestContactContainer() {
    }

    public RequestContactContainer(User userGettingNotification, ArrayList<User> usersRequestingContact) {
        this.userGettingNotification = userGettingNotification;
        this.usersRequestingContact = usersRequestingContact;
    }

    //Getters and setter
    public User getUserGettingNotification() {
        return userGettingNotification;
    }

    public void setUserGettingNotification(User userGettingNotification) {
        this.userGettingNotification = userGettingNotification;
    }

    public ArrayList<User> getUsersRequestingContact() {
        return usersRequestingContact;
    }

    public void setUsersRequestingContact(ArrayList<User> usersRequestingContact) {
        this.usersRequestingContact = usersRequestingContact;
    }
}
