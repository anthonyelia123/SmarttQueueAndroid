package me.queue.smartqueue.Model;

public class QueueAndServiceTime {
    private double waitingService;
    private double waitingQueue;

    public QueueAndServiceTime(double waitingService, double waitingQueue) {
        this.waitingService = waitingService;
        this.waitingQueue = waitingQueue;
    }

    public double getWaitingService() {
        return waitingService;
    }

    public void setWaitingService(double waitingService) {
        this.waitingService = waitingService;
    }

    public double getWaitingQueue() {
        return waitingQueue;
    }

    public void setWaitingQueue(double waitingQueue) {
        this.waitingQueue = waitingQueue;
    }
}
