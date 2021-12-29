package cz.praguecityuniversity;

public class EthernetNetworkAdapter extends NetworkAdapter{

    EthernetNetworkAdapter(String deviceName, PortInterface[] arrayOfPortInterfaces) {
        super(deviceName, arrayOfPortInterfaces);
    }

    @Override
    public Event handleEvent(Event event) {

        return event;
    }
}

