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

    public Event processFinalEvent(Event event) throws EventFinished {
        if (this.ethernetNetworkAdapter.containsIP(event.frame.message.getDestinationIP())) {
            if (event.frame.message.getTypeOfMessage() == TypeOfMessage.PING) {
                System.out.println(this.deviceName + " received ping from IP: " + event.frame.message.getSourceIP().getIPAddressStr());
                event.frame.message.setTypeOfMessage(TypeOfMessage.MESSAGE);
                event.frame.message.setDestinationIP(event.frame.message.getSourceIP());
                event.frame.message.setSourceIP(event.frame.message.getDestinationIP());
            } else {
                System.out.println(this.deviceName + " received data: " + event.frame.message.getData() + " from IP: " + event.frame.message.getSourceIP().getIPAddressStr());
                throw new EventFinished("Event \"" + event.eventName + "\" finished at time: " + event.startingTime);
            }
        }
        return event;
    }
}