package me.queue.smartqueue.Model;

public class QueuePeople {

    User user;
    double avgWaitingTime;
    int pos;
    String createdAt;

    public QueuePeople(User user, double avgWaitingTime, int pos, String createdAt){
        this.user = user;
        this.avgWaitingTime = avgWaitingTime;
        this.pos = pos;
        this.createdAt = createdAt;

    }

    public QueuePeople(){}

    public User getUser(){
        return user;
    }
    public void setUser(User user){
        this.user = user;
    }

    public double getAvgWaitingTime(){
        return avgWaitingTime;
    }
    public void setAvgWaitingTime(double avgWaitingTime){
        this.avgWaitingTime = avgWaitingTime;
    }

    public int getPos(){
        return pos;
    }
    public void setPos(int pos){
        this.pos = pos;
    }

    public String getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }
}
