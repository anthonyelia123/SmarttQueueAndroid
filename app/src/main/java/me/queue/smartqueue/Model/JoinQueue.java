package me.queue.smartqueue.Model;

public class JoinQueue {
    String id;
    String queueId;
    String userId;
    String createdAt;
    String finishedAt;
    boolean finished;
    boolean skipped;
    int position;
    double waitingTime;

    public JoinQueue(String id, String queueId, String userId,
                     String createdAt, String finishedAt, boolean finished, boolean skipped, int position, double waitingTime){
        this.id = id;
        this.queueId = queueId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.finishedAt = finishedAt;
        this.finished = finished;
        this.skipped = skipped;
        this.position = position;
        this.waitingTime = waitingTime;
    }

    public JoinQueue(){}
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getQueueId(){
        return queueId;
    }
    public void setQueueId(String queueId){
        this.queueId = queueId;
    }

    public String getUserId(){
        return userId;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getCreatedAt(){
        return createdAt;
    }
    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getFinishedAt(){
        return finishedAt;
    }
    public void setFinishedAt(String finishedAt){
        this.finishedAt = finishedAt;
    }

    public boolean isFinished(){
        return finished;
    }
    public void setFinished(boolean finished){
        this.finished = finished;
    }

    public boolean isSkipped(){
        return skipped;
    }
    public void setSkipped(boolean skipped){
        this.skipped = skipped;
    }

    public int getPosition(){
        return position;
    }
    public void setPosition(int position){
        this.position = position;
    }

    public double getWaitingTime(){
        return waitingTime;
    }
    public void setWaitingTime(double waitingTime){
        this.waitingTime = waitingTime;
    }
}
