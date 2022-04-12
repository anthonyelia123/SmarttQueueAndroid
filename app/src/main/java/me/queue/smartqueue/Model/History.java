package me.queue.smartqueue.Model;

public class History {

    public String queueId;
    public String username;
    public String joinedAt;
    public String finishedAt;
    public String waitingTime;


    public History(String queueId ,String username,String joinedAt,String finishedAt,String waitingTime){
        this.queueId = queueId;
        this.username = username;
        this.joinedAt = joinedAt;
        this.finishedAt = finishedAt;
        this.waitingTime = waitingTime;
    }
    public History(){}

    public String getQueueId(){
        return queueId;
    }
    public void setQueueId(String queueId){
        this.queueId = queueId;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
       this.username = username;
   }

   public String getJoinedAt(){
       return joinedAt;
   }
   public void setJoinedAt(String joinedAt){
       this.joinedAt = joinedAt;
   }

   public String getFinishedAt(){
       return finishedAt;
   }
   public void setFinishedAt(String finishedAt){
       this.finishedAt = finishedAt;
   }

   public String getWaitingTime(){
       return waitingTime;
   }
   public void setWaitingTime(String waitingTime){
       this.waitingTime = waitingTime;
   }
}
