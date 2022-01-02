package cz.praguecityuniversity;

abstract class NetworkAdapter extends Device implements EventHandler{
    Device device;
    NetworkAdapter(String deviceName, Device device) {
        super(deviceName);
        this.device = device;
    }
}
