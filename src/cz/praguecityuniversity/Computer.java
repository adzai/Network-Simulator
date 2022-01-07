package cz.praguecityuniversity;

public class Computer extends Device {

    Computer(String deviceName, TypeofEntity typeofEntity) {
        super(deviceName,typeofEntity);
    }

    @Override
    public Event handleEvent(Event event) {
        if (event.getPreviousEntity() == ethernetNetworkAdapter) {
            System.out.println(this.deviceName + " received data: " + event.data + " from IP: " + event.sourceIPAddress.getIPAddressStr());
            return null;
        }
        event.entity = this.ethernetNetworkAdapter;
        event.startingTime = event.startingTime + 3;
        event.setPreviousEntity(this);
        return event;
    }
}
