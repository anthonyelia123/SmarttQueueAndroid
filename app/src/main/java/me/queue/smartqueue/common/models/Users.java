package me.queue.smartqueue.common.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Users implements Serializable {
    private String queueOwner;
    private String joiningTime;
    private List<UserJoinStatus> users;

    public Users(String queueOwner, List<UserJoinStatus> userJoinStatuses, String joiningTime) {
        this.queueOwner = queueOwner;
        this.users = userJoinStatuses;
        this.joiningTime = joiningTime;
    }

    public String getQueueOwner() {
        return queueOwner;
    }

    public void setQueueOwner(String queueOwner) {
        this.queueOwner = queueOwner;
    }

    public List<UserJoinStatus> getUsers() {
        return users;
    }

    public void setUsers(List<UserJoinStatus> users) {
        this.users = users;
    }

    public String getJoiningTime() {
        return joiningTime;
    }

    public void setJoiningTime(String joiningTime) {
        this.joiningTime = joiningTime;
    }

    @Override
    public String toString() {
        return "JoiningProcess{" +
                "ownerId='" + queueOwner + '\'' +
                ", userJoinStatuses=" + users +
                '}';
    }
}
