package cz.praguecityuniversity;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Responsible for scheduling and then calling the
 * relevant event handlers.
 */
public class EventScheduler {
    PriorityQueue<Event> eventQueue;
    private final EventLogger logger;
    private static EventScheduler eventSchedulerInstance = null;

    private EventScheduler(EventLogger logger) {
        eventQueue = new PriorityQueue<>(new EventComparator());
        this.logger = logger;
    }

    public static EventScheduler getInstance(EventLogger logger) {
        {
            if (eventSchedulerInstance == null)
                eventSchedulerInstance = new EventScheduler(logger);

            return eventSchedulerInstance;
        }
    }

    /**
     * Inserts an event to the correct position in the PriorityQueue.
     * @param event Event to be added.
     */
    void schedule(Event event) {
        eventQueue.add(event);
    }

    /**
     * Processes the next event in the PriorityQueue.
     */
    void next() {
        Event event = eventQueue.poll();
        if (event != null) {
            logger.log(event);
            Event newEvent;
            try {
                newEvent = event.getEntity().handleEvent(event, logger);
            } catch (EventFinished e) {
                logger.logInfo(event.getStartingTime(), e.getMessage());
                newEvent = null;
            }
            if (newEvent != null) {
                this.schedule(newEvent);
            }
        }
    }

    /**
     * @return Returns whether there are any more events to be processed.
     */
    boolean isDone() {
        return eventQueue.size() == 0;
    }
}

/**
 * Comparator for inserting events to the PriorityQueue based
 * on their starting time.
 */
class EventComparator implements Comparator<Event> {
    public int compare(Event e1, Event e2) {
        if (e1.getStartingTime() > e2.getStartingTime())
            return 1;
        else if (e1.getStartingTime() < e2.getStartingTime())
            return -1;
        return 0;
    }
}

