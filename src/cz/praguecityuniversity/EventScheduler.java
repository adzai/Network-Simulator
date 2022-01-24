package cz.praguecityuniversity;

import java.util.Comparator;
import java.util.PriorityQueue;

public class EventScheduler {
    PriorityQueue<Event> eventQueue;
    private final EventLogger logger;

    EventScheduler(EventLogger logger) {
        eventQueue = new PriorityQueue<>(new EventComparator());
        this.logger = logger;
    }

    void schedule(Event event){
        eventQueue.add(event);
    }
    void next(){
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

    boolean isDone() {
        return eventQueue.size() == 0;
    }
}
class EventComparator implements Comparator<Event> {
    public int compare(Event e1, Event e2) {
        if (e1.getStartingTime() > e2.getStartingTime())
            return 1;
        else if (e1.getStartingTime() < e2.getStartingTime())
            return -1;
        return 0;
    }
}

