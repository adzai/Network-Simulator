package cz.praguecityuniversity;

public interface EventHandler {
    Event handleEvent(Event event, EventLogger logger) throws EventFinished;
}