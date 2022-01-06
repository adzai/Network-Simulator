package cz.praguecityuniversity;

public class Computer extends Device {
    EthernetNetworkAdapter ethernetNetworkAdapter;
    Computer(String deviceName, EthernetNetworkAdapter ethernetNetworkAdapter,TypeofEntity typeofEntity) {
        super(deviceName,typeofEntity);
        this.ethernetNetworkAdapter = ethernetNetworkAdapter;
    }

    @Override
    public Event handleEvent(Event event) {
        event.entity = this.ethernetNetworkAdapter;
        event.startingTime = event.startingTime + 3;
        event.setPreviousEntity(this);
        return event;
    }
}
