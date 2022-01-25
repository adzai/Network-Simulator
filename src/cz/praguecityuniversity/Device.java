package cz.praguecityuniversity;

/**
 * Implementation of Device
 */
public class Device extends Entity {
    String deviceName;
    EthernetNetworkAdapter ethernetNetworkAdapter;
    WirelessNetworkAdapter wirelessNetworkAdapter;
    String macAddress;
    private final IPProtocol IPProtocol;

    Device(String deviceName, TypeofEntity typeofEntity, String macAddress, IPProtocol IPProtocol) {
        super(typeofEntity);
        this.deviceName = deviceName;
        this.ethernetNetworkAdapter = null;
        this.wirelessNetworkAdapter = null;
        this.macAddress = macAddress;
        this.IPProtocol = IPProtocol;
    }

    public IPProtocol getIPProtocol() {
        return IPProtocol;
    }

    /**
     * Gets either ethernet network adapter or wireless network adapter.
     * @param device
     * @return Returns ethernet network adapter if one of the defined port interfaces has a cabled connection,
     * otherwise wireless network adapter.
     */
    public NetworkAdapter getNetworkAdapter(Device device) {
        for (PortInterface portInterface : device.ethernetNetworkAdapter.arrayOfPortInterfaces) {
            if (portInterface.getConnection() != null) {
                return device.ethernetNetworkAdapter;
            }
        }
        return device.wirelessNetworkAdapter;
    }

    /**
     * Redirects event to the chosen network adapter and
     * sets the correct port interface trough which the event will be sent by the network adapter.
     * Also, it adds delay simulating passing of time.
     * @param event Event
     * @param logger Logger for logging information related to event handling
     * @return Returns handled event
     * @throws EventFinished Thrown when the event is finished on the device
     */
    @Override
    public Event handleEvent(Event event, EventLogger logger) throws EventFinished {
        event = IPProtocol.processFinalEvent(event, logger, this);
        int correctPortInterface = -1;
        try {
            correctPortInterface = IPProtocol.getCorrectPortInterface(event.getFrame().getMessage().getDestinationIP(), this);
        } catch (RouteNotFound routeNotFound) {
            routeNotFound.printStackTrace();
        }
        event.setCorrectPortInterface(correctPortInterface);
        logger.logInfo(event.getStartingTime(), "Chosen port interface: " + event.getCorrectPortInterface());
        event.setEntity(getNetworkAdapter(this));
        event.setStartingTime(event.getStartingTime() + 5);
        event.setPreviousEntity(this);
        return event;
    }
}