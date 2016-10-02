package com.tiy;

/**
 * Created by jessicatracy on 10/1/16.
 */
public class FriendConnectionContainer {
    int userId;
    int userWhoWantsToBeFriendId;

    public FriendConnectionContainer() {

    }

    public FriendConnectionContainer(int userId, int userWhoWantsToBeFriendId) {
        this.userId = userId;
        this.userWhoWantsToBeFriendId = userWhoWantsToBeFriendId;
    }

    //Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserWhoWantsToBeFriendId() {
        return userWhoWantsToBeFriendId;
    }

    public void setUserWhoWantsToBeFriendId(int userWhoWantsToBeFriendId) {
        this.userWhoWantsToBeFriendId = userWhoWantsToBeFriendId;
    }
}
