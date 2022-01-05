package cz.praguecityuniversity;

public class WirelessNetworkAdapter extends NetworkAdapter{

    WirelessNetworkAdapter(String deviceName, Device device,TypeofEntity typeofEntity) {
        super(deviceName, device,typeofEntity);
    }

    @Override
    public Event handleEvent(Event event) {

        return event;
    }
}
