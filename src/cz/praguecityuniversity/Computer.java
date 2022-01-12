package cz.praguecityuniversity;

public class Computer extends Device {

    Computer(String deviceName, TypeofEntity typeofEntity, String macAddress) {
        super(deviceName, typeofEntity, macAddress);
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
