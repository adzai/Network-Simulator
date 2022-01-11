package cz.praguecityuniversity;

public class WirelessNetworkAdapter extends NetworkAdapter{

    WirelessNetworkAdapter(Device device,TypeofEntity typeofEntity) {
        super(device,typeofEntity);
    }

    @Override
    public Event handleEvent(Event event) {

        return event;
    }
}
