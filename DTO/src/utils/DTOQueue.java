package utils;

public class DTOQueue {
    private final Integer queueSize;
    private final Integer waiting;
    private final Integer finished;


    public DTOQueue(int queueSize, int waiting, int finished) {
        this.queueSize = queueSize;
        this.waiting = waiting;
        this.finished = finished;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public Integer getWaiting() {
        return waiting;
    }

    public Integer getFinished() {
        return finished;
    }
}
