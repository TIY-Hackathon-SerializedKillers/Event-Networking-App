package com.tiy;

/**
 * Created by jessicatracy on 10/1/16.
 */
public class IDContainer {
    int userId;
    int eventId;

    public IDContainer() {
    }

    public IDContainer(int userId, int eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    //Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
