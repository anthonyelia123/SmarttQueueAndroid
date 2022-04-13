package me.queue.smartqueue.common.models;

import java.io.Serializable;

public class UserJoinStatus implements Serializable {
    private String userId;
    private String joinDate;
    private boolean finished;
    private boolean delayed;
    private int server;

    public UserJoinStatus( String joinDate,boolean finished, String userId, boolean delayed, int server) {
        this.userId = userId;
        this.joinDate = joinDate;
        this.finished = finished;
        this.delayed = delayed;
        this.server = server;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isDelayed() {
        return delayed;
    }

    public int getServer() {
        return server;
    }

    public void setServer(int server) {
        this.server = server;
    }

    public void setDelayed(boolean delayed) {
        this.delayed = delayed;
    }

    @Override
    public String toString() {
        return "UserJoinStatus{" +
                "userId='" + userId + '\'' +
                ", joinDate='" + joinDate + '\'' +
                ", finished=" + finished +
                '}';
    }
}
