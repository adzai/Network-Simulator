package cz.praguecityuniversity;

public class CabledConnection extends Connection{
    EthernetNetworkAdapter ethernetNetworkAdapter;
    CabledConnection(EthernetNetworkAdapter ethernetNetworkAdapter) {
        this.ethernetNetworkAdapter = ethernetNetworkAdapter;
    }
    @Override
    public Event handleEvent(Event event) {

        return event;
    }
}