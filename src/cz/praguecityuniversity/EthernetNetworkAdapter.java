package cz.praguecityuniversity;

public class EthernetNetworkAdapter extends NetworkAdapter{


    EthernetNetworkAdapter(String deviceName, PortInterface[] arrayOfPortInterfaces, Device device) {
        super(deviceName, arrayOfPortInterfaces, device);
    }

    @Override
    public Event handleEvent(Event event) {

        return event;
    }
}

