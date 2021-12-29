package cz.praguecityuniversity;

abstract class NetworkAdapter extends Device implements EventHandler{
    PortInterface[] arrayOfPortInterfaces;
    Device device;
    NetworkAdapter(String deviceName, PortInterface[] arrayOfPortInterfaces, Device device) {
        super(deviceName);
        this.arrayOfPortInterfaces = arrayOfPortInterfaces;
        this.device = device;
    }
}
