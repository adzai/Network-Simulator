package cz.praguecityuniversity;

public class Computer extends Device {

    Computer(String deviceName, TypeofEntity typeofEntity, String macAddress) {
        super(deviceName, typeofEntity, macAddress);
    }

    @Override
    public Event handleEvent(Event event, EventLogger logger) throws EventFinished {
        event = this.processFinalEvent(event, logger);
        event.setEntity(this.getNetworkAdapter());
        event.setStartingTime(event.getStartingTime() + 3);
        event.setPreviousEntity(this);
        return event;
    }
}