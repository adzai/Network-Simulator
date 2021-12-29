package cz.praguecityuniversity;

public class WirelessNetworkAdapter extends NetworkAdapter{

    WirelessNetworkAdapter(String deviceName, PortInterface[] arrayOfPortInterfaces) {
        super(deviceName, arrayOfPortInterfaces);
    }

    @Override
    public Event handleEvent(Event event) {

        return event;
    }
}
