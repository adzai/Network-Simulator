package cz.praguecityuniversity;

import java.util.PriorityQueue;

public class EventScheduler {
    private PriorityQueue<Event> eventQueue;

    void schedule(Event event){
        Event newEvent = event.device.handleEvent(event);
        System.out.println(newEvent.device);
    }
    void next(){}
}

