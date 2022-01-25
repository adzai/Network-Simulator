package cz.praguecityuniversity;

/**
 * Thrown when this is the last event to be processed
 */
public class EventFinished extends Exception {
    public EventFinished(String errorMsg) {
        super(errorMsg);
    }
}
