package cz.praguecityuniversity;

/**
 * Cabled connection that can be attached to an ethernet network
 * adapter on each side.
 */
public class CabledConnection extends Connection {
    EthernetNetworkAdapter ethernetNetworkAdapter1;
    EthernetNetworkAdapter ethernetNetworkAdapter2;

    CabledConnection(EthernetNetworkAdapter ethernetNetworkAdapter1,
                     EthernetNetworkAdapter ethernetNetworkAdapter2) {
        this.ethernetNetworkAdapter1 = ethernetNetworkAdapter1;
        this.ethernetNetworkAdapter2 = ethernetNetworkAdapter2;
        this.setBandwidth(4);
    }

    /**
     * Chooses which way to send the message based on where it came from, adds
     * delay simulating passing of time.
     * @param event Current event to be processed.
     * @param logger Logger that can be used to provide additional information.
     * @return Returns the next event.
     */
    @Override
    public Event handleEvent(Event event, EventLogger logger) {
        if (event.getPreviousEntity() == ethernetNetworkAdapter1) {
            event.setEntity(ethernetNetworkAdapter2);
        } else {
            event.setEntity(ethernetNetworkAdapter1);
        }
        event.setStartingTime(event.getStartingTime() + this.getBandwidth());
        event.setPreviousEntity(this);
        return event;
    }
}