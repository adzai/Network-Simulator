package cz.praguecityuniversity;


public abstract class Device extends Entity{
    String deviceName;
    EthernetNetworkAdapter ethernetNetworkAdapter;

    Device(String deviceName, TypeofEntity typeofEntity) {
        super(typeofEntity);
        this.deviceName = deviceName;
        this.ethernetNetworkAdapter = null;
    }

    public Event processFinalEvent(Event event) throws EventFinished {
        if (this.ethernetNetworkAdapter.containsIP(event.message.getDestinationIP())) {
            if (event.message.getTypeOfMessage() == TypeOfMessage.PING) {
                System.out.println(this.deviceName + " received ping from IP: " + event.message.getSourceIP().getIPAddressStr());
                event.message.setTypeOfMessage(TypeOfMessage.MESSAGE);
                event.message.setDestinationIP(event.message.getSourceIP());
                event.message.setSourceIP(event.message.getDestinationIP());
            } else {
                System.out.println(this.deviceName + " received data: " + event.message.getData() + " from IP: " + event.message.getSourceIP().getIPAddressStr());
                throw new EventFinished("Event \"" + event.eventName + "\" finished at time: " + event.startingTime);
            }
        }
        return event;
    }
}