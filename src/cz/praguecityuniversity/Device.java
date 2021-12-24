package cz.praguecityuniversity;

public class Device implements EventHandler{
    private String deviceName;
    WirelessNetworkAdapter wirelessNetworkAdapter;
    EthernetNetworkAdapter ethernetNetworkAdapter;
    PortInterface[] arrayOfPortInterfaces;

    @Override
    public void handleEvent(Event event) {}
}
