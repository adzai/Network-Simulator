package cz.praguecityuniversity;

public class Computer extends Device {
    EthernetNetworkAdapter ethernetNetworkAdapter;
    Computer(String deviceName, EthernetNetworkAdapter ethernetNetworkAdapter) {
        super(deviceName);
        this.ethernetNetworkAdapter = ethernetNetworkAdapter;
    }

    @Override
    public Event handleEvent(Event event) {
        event.device = this.ethernetNetworkAdapter;
        event.startingTime = event.startingTime + 3;
        return event;
    }
}
