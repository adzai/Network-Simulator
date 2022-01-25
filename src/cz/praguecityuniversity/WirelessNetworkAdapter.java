package cz.praguecityuniversity;

/**
 * Placeholder for a wireless network adapter implementation.
 */
public class WirelessNetworkAdapter extends NetworkAdapter {

    WirelessNetworkAdapter(Device device, TypeofEntity typeofEntity) {
        super(device, typeofEntity);
    }

    @Override
    public Event handleEvent(Event event, EventLogger logger) {

        return event;
    }
}
