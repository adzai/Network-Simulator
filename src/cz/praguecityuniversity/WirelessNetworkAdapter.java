package cz.praguecityuniversity;

public class WirelessNetworkAdapter extends NetworkAdapter{
    WirelessNetworkAdapter(String deviceName, PortInterface[] arrayOfPortInterfaces, Device device) {
        super(deviceName, arrayOfPortInterfaces, device);
    }

    @Override
    public Event handleEvent(Event event) {

        return event;
    }
}
