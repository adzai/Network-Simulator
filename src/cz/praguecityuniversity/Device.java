package cz.praguecityuniversity;

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

    public NetworkAdapter getNetworkAdapter(Device device) {
        for (PortInterface portInterface : device.ethernetNetworkAdapter.arrayOfPortInterfaces) {
            if (portInterface.getConnection() != null) {
                return device.ethernetNetworkAdapter;
            }
        }
        return device.wirelessNetworkAdapter;
    }

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