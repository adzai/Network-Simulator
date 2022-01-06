package cz.praguecityuniversity;

public class CabledConnection extends Connection{
    EthernetNetworkAdapter ethernetNetworkAdapter1;
    EthernetNetworkAdapter ethernetNetworkAdapter2;
    CabledConnection(EthernetNetworkAdapter ethernetNetworkAdapter1,
                     EthernetNetworkAdapter ethernetNetworkAdapter2,
                     TypeofEntity typeofEntity) {
        super(typeofEntity);
        this.ethernetNetworkAdapter1 = ethernetNetworkAdapter1;
        this.ethernetNetworkAdapter2 = ethernetNetworkAdapter2;
    }
    @Override
    public Event handleEvent(Event event) {
        if(event.getPreviousEntity() == ethernetNetworkAdapter1){
            event.entity = ethernetNetworkAdapter2;
        } else {
            event.entity = ethernetNetworkAdapter1;
        }
        event.startingTime += 3;
        event.setPreviousEntity(this);
        return event;
    }
}