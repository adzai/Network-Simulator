package cz.praguecityuniversity;

public class CabledConnection extends Connection {
    EthernetNetworkAdapter ethernetNetworkAdapter1;
    EthernetNetworkAdapter ethernetNetworkAdapter2;

    CabledConnection(EthernetNetworkAdapter ethernetNetworkAdapter1,
                     EthernetNetworkAdapter ethernetNetworkAdapter2) {
        this.ethernetNetworkAdapter1 = ethernetNetworkAdapter1;
        this.ethernetNetworkAdapter2 = ethernetNetworkAdapter2;
    }

    @Override
    public Event handleEvent(Event event, EventLogger logger) {
        if (event.getPreviousEntity() == ethernetNetworkAdapter1) {
            event.setEntity(ethernetNetworkAdapter2);
        } else {
            event.setEntity(ethernetNetworkAdapter1);
        }
        event.setStartingTime(event.getStartingTime() + 3);
        event.setPreviousEntity(this);
        return event;
    }
}