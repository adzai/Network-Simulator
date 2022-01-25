package cz.praguecityuniversity;

/**
 * Every Entity that works with events has to implement handleEvent to be able
 * to process its events through the EventScheduler.
 */
public interface EventHandler {
    Event handleEvent(Event event, EventLogger logger) throws EventFinished;
}