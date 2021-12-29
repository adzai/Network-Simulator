package cz.praguecityuniversity;

abstract class NetworkAdapter extends Device implements EventHandler{
    PortInterface[] arrayOfPortInterfaces;

    NetworkAdapter(String deviceName, PortInterface[] arrayOfPortInterfaces) {
        super(deviceName);
        this.arrayOfPortInterfaces = arrayOfPortInterfaces;
    }
}
