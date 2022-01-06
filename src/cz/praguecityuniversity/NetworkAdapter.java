package cz.praguecityuniversity;

abstract class NetworkAdapter extends Device implements EventHandler{
    Device device;
    NetworkAdapter(String deviceName, Device device,TypeofEntity typeofEntity) {
        super(deviceName,typeofEntity);
        this.device = device;
    }
}
