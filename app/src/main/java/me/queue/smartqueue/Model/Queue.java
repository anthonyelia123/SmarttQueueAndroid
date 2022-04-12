package me.queue.smartqueue.Model;

import java.io.Serializable;

public class Queue implements Serializable {
    public String id;
    public String name;
    public int maxSize;
    public int currectUsers;
    public String location;
    public String field;
    public double lambda;
    public double mue;
    public double averageWaitingTime;
    public double waitingTime;
    public int currentPosition;
    public int myPosition;
    public int nbOfCounters;
    public String userId;
    public boolean isJoining;
    public Queue(String id, String name ,int maxSize, int currentUsers, String location,String field,double lambda, double mue, double averageWaitingTime,
                 int currentPosition, int myPosition, int nbOfCounters, String userId, boolean isJoining){
        this.id = id;
        this.name = name;
        this.maxSize = maxSize;
        this.currectUsers = currentUsers;
        this.location = location;
        this.field = field;
        this.lambda = lambda;
        this.mue = mue;
        this.averageWaitingTime = averageWaitingTime;
        this.currentPosition = currentPosition;
        this.myPosition = myPosition;
        this.nbOfCounters = nbOfCounters;
        this.userId = userId;
        this.isJoining = isJoining;
        this.waitingTime = 0;
    }

    public Queue(){}
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public int getMaxSize(){
        return maxSize;
    }
    public void setMaxSize(int maxSize){
        this.maxSize = maxSize;
    }

    public int getCurrectUsers(){
        return currectUsers;
    }
    public void setCurrectUsers(int currectUsers){
        this.currectUsers = currectUsers;
    }

    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }

    public String getField(){
        return field;
    }
    public void setField(String field){
        this.field = field;
    }

    public double getLambda(){
        return lambda;
    }
    public void setLambda(double lambda){
        this.lambda = lambda;
    }

    public double getMue(){
        return mue;
    }
    public void setMue(double mue){
        this.mue = mue;
    }

    public double getAverageWaitingTime(){
        return averageWaitingTime;
    }
    public void setAverageWaitingTime(double averageWaitingTime){
        this.averageWaitingTime = averageWaitingTime;
    }

    public double getWaitingTime(){
        return waitingTime;
    }
    public void setWaitingTime(double waitingTime){
        this.waitingTime = waitingTime;
    }

    public int getCurrentPosition(){
        return currentPosition;
    }
    public void setCurrentPosition(int currentPosition){
        this.currentPosition = currentPosition;
    }
    public int getMyPosition(){
        return myPosition;
    }
    public void setMyPosition(int myPosition){
        this.myPosition = myPosition;
    }

    public int getNbOfCounters(){
        return nbOfCounters;
    }
    public void setNbOfCounters(int nbOfCounters){
        this.nbOfCounters = nbOfCounters;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public boolean isJoining(){
        return isJoining;
    }
    public void setJoining(boolean isJoining){
        this.isJoining = isJoining;
    }
}
