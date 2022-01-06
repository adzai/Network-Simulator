package cz.praguecityuniversity;

import java.util.Comparator;
import java.util.PriorityQueue;

public class EventScheduler {
    PriorityQueue<Event> eventQueue;

    EventScheduler() {
        eventQueue = new PriorityQueue<>(new EventComparator());
    }

    void schedule(Event event){
        eventQueue.add(event);
    }
    void next(){
       Event event = eventQueue.poll();
       if (event != null) {
           Event newEvent = event.entity.handleEvent(event);
           this.schedule(newEvent);
       }
    }
}
class EventComparator implements Comparator<Event> {
    public int compare(Event e1, Event e2) {
        if (e1.startingTime > e2.startingTime)
            return 1;
        else if (e1.startingTime < e2.startingTime)
            return -1;
        return 0;
    }
}

