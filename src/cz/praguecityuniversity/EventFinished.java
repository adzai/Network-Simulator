package cz.praguecityuniversity;

public class EventFinished extends Exception {
    public EventFinished(String errorMsg) {
        super(errorMsg);
    }
}
