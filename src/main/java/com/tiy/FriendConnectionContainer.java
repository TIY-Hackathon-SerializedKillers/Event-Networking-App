package com.tiy;

/**
 * Created by jessicatracy on 10/1/16.
 */
public class FriendConnectionContainer {
    int userId;
    int friendId;

    public FriendConnectionContainer() {
    }

    public FriendConnectionContainer(int userId, int friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    //Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }
}
