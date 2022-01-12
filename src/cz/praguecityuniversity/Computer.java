package cz.praguecityuniversity;

public class Computer extends Device {

    Computer(String deviceName, TypeofEntity typeofEntity, String macAddress) {
        super(deviceName, typeofEntity, macAddress);
    }

    @Override
    public Event handleEvent(Event event) {
        if (event.getPreviousEntity() == ethernetNetworkAdapter) {
            System.out.println(this.deviceName + " received data: " + event.frame.message.getData() +
                    " from IP: " + event.frame.message.getSourceIP().getIPAddressStr());
            return null;
        }
        event.entity = this.ethernetNetworkAdapter;
        event.startingTime = event.startingTime + 3;
        event.setPreviousEntity(this);
        return event;
    }
}
