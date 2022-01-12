package cz.praguecityuniversity;

public class Computer extends Device {

    Computer(String deviceName, TypeofEntity typeofEntity) {
        super(deviceName, typeofEntity);
    }

    @Override
    public Event handleEvent(Event event) {
        try {
            event = this.processFinalEvent(event);
        } catch (EventFinished e) {
            System.out.println(e.getMessage());
            return null;
        }
        event.entity = this.ethernetNetworkAdapter;
        event.startingTime = event.startingTime + 3;
        event.setPreviousEntity(this);
        return event;
    }
}
