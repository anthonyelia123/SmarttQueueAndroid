package me.queue.smartqueue.main.data.models;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QueueModel implements Serializable {
    @PropertyName("queueName")
    private String queueName;
    @PropertyName("queueId")
    private String queueId;
    @PropertyName("isFinished")
    private String isFinished;
    @PropertyName("createdAt")
    private String createdAt;
    @PropertyName("endedAt")
    private String endedAt;
    @PropertyName("ownerId")
    private String ownerId;
    @PropertyName("location")
    private String location;
    @PropertyName("maxSize")
    private String maxSize;
    @PropertyName("mue")
    private String mue;
    @PropertyName("lambda")
    private String lambda;
    @PropertyName("field")
    private String field;
    @PropertyName("counter")
    private String counter;
    @PropertyName("finishedId")
    private String finishedId;

    public boolean isAdmin = false;


    public QueueModel(String queueName, String queueId, String isFinished, String createdAt, String endedAt, String ownerId, String location, String maxSize, String mue, String lambda, String field, String counter, String finishedId) {
        this.queueName = queueName;
        this.queueId = queueId;
        this.isFinished = isFinished;
        this.createdAt = createdAt;
        this.endedAt = endedAt;
        this.ownerId = ownerId;
        this.location = location;
        this.maxSize = maxSize;
        this.mue = mue;
        this.lambda = lambda;
        this.field = field;
        this.counter = counter;
        this.finishedId = finishedId;
    }

    public String getCounter() {
        return counter;
    }

    public void setCounter(String counter) {
        this.counter = counter;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getQueueId() {
        return queueId;
    }

    public void setQueueId(String queueId) {
        this.queueId = queueId;
    }

    public String isFinished() {
        return isFinished;
    }

    public void setFinished(String finished) {
        isFinished = finished;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(String endedAt) {
        this.endedAt = endedAt;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public String getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(String isFinished) {
        this.isFinished = isFinished;
    }

    public String getFinishedId() {
        return finishedId;
    }

    public void setFinishedId(String finishedId) {
        this.finishedId = finishedId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(String maxSize) {
        this.maxSize = maxSize;
    }

    public String getMue() {
        return mue;
    }

    public void setMue(String mue) {
        this.mue = mue;
    }

    public String getLambda() {
        return lambda;
    }

    public void setLambda(String lambda) {
        this.lambda = lambda;
    }


    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public String toString() {
        return "QueueModel{" +
                "queueId='" + queueId + '\'' +
                ", isFinished=" + isFinished +
                ", createdAt='" + createdAt + '\'' +
                ", endedAt='" + endedAt + '\'' +
                ", ownerId='" + ownerId + '\'' +
                ", location='" + location + '\'' +
                ", maxSize='" + maxSize + '\'' +
                ", mue='" + mue + '\'' +
                ", lambda='" + lambda + '\'' +
                '}';
    }
}
