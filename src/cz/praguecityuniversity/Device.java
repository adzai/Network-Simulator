package cz.praguecityuniversity;

public abstract class Device extends Entity{
    String deviceName;
    EthernetNetworkAdapter ethernetNetworkAdapter;
    String macAddress;

    Device(String deviceName, TypeofEntity typeofEntity,String macAddress) {
        super(typeofEntity);
        this.deviceName = deviceName;
        this.ethernetNetworkAdapter = null;
        this.macAddress = macAddress;
    }

    public Event processFinalEvent(Event event, EventLogger logger) throws EventFinished {
        if (this.ethernetNetworkAdapter.containsIP(event.getFrame().getMessage().getDestinationIP())) {
            if (event.getFrame().getMessage().getTypeOfMessage() == TypeOfMessage.PING) {
                logger.logInfo(event.getStartingTime(), this.deviceName + " received ping from IP: " + event.getFrame().getMessage().getSourceIP().getIPAddressStr());
                event.getFrame().getMessage().setTypeOfMessage(TypeOfMessage.MESSAGE);
                event.getFrame().getMessage().setDestinationIP(event.getFrame().getMessage().getSourceIP());
                event.getFrame().getMessage().setSourceIP(event.getFrame().getMessage().getDestinationIP());
            } else {
                logger.logInfo(event.getStartingTime(), this.deviceName + " received data: " + event.getFrame().getMessage().getData() + " from IP: " + event.getFrame().getMessage().getSourceIP().getIPAddressStr());
                throw new EventFinished("Event \"" + event.getEventName() + "\" finished at time: " + event.getStartingTime());
            }
        }
        return event;
    }
}