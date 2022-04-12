package me.queue.smartqueue.common.models;

public class UserIdModel {
    private String userId;
    private String fullName;

    public UserIdModel(String userId, String fullName) {
        this.userId = userId;
        this.fullName = fullName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @Override
    public String toString() {
        return "UserIdModel{" +
                "userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
