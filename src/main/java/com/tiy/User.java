package com.tiy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by jessicatracy on 9/29/16.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    int id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String firstName;

    @Column(nullable = false)
    String lastName;

    @Column
    String techSkills;

//    @Column
//    ArrayList<User> usersWhoIHaveFriendRequestsFrom = new ArrayList<>();

//    @ManyToOne
//    UserEvent userEvent;

//    ArrayList<User> listOfPeopleWhoCanSeeMyStuff = new ArrayList<>();

//    HashMap<User, String> listOfPeopleAndStatusForSeeingMyStuff = new HashMap<>();

    public User() {
    }

    public User(String email, String password, String firstName, String lastName, String techSkills) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.techSkills = techSkills;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTechSkills() {
        return techSkills;
    }

    public void setTechSkills(String techSkills) {
        this.techSkills = techSkills;
    }

//    public ArrayList<User> getUsersWhoIHaveFriendRequestsFrom() {
//        return usersWhoIHaveFriendRequestsFrom;
//    }
//
//    public void setUsersWhoIHaveFriendRequestsFrom(ArrayList<User> usersWhoIHaveFriendRequestsFrom) {
//        this.usersWhoIHaveFriendRequestsFrom = usersWhoIHaveFriendRequestsFrom;
//    }
//
//    public ArrayList<User> getListOfPeopleWhoCanSeeMyStuff() {
//        return listOfPeopleWhoCanSeeMyStuff;
//    }
//
//    public void setListOfPeopleWhoCanSeeMyStuff(ArrayList<User> listOfPeopleWhoCanSeeMyStuff) {
//        this.listOfPeopleWhoCanSeeMyStuff = listOfPeopleWhoCanSeeMyStuff;
//    }

//    public HashMap<User, String> getListOfPeopleAndStatusForSeeingMyStuff() {
//        return listOfPeopleAndStatusForSeeingMyStuff;
//    }
//
//    public void setListOfPeopleAndStatusForSeeingMyStuff(HashMap<User, String> listOfPeopleAndStatusForSeeingMyStuff) {
//        this.listOfPeopleAndStatusForSeeingMyStuff = listOfPeopleAndStatusForSeeingMyStuff;
//    }
}
