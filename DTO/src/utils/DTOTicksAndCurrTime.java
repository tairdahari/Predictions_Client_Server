package utils;

public class DTOTicksAndCurrTime {
    private final String tick;
    private final String currTime;

    public DTOTicksAndCurrTime(String tick, String currTime) {
        this.tick = tick;
        this.currTime = currTime;
    }

    public String getCurrTime() {
        return currTime;
    }

    public String getTick() {
        return tick;
    }
}
