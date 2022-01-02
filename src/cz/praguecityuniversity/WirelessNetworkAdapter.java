package cz.praguecityuniversity;

public class WirelessNetworkAdapter extends NetworkAdapter{

    WirelessNetworkAdapter(String deviceName, Device device) {
        super(deviceName, device);
    }

    @Override
    public Event handleEvent(Event event) {

        return event;
    }
}
