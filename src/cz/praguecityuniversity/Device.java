package cz.praguecityuniversity;


public abstract class Device extends Entity{
    String deviceName;
    EthernetNetworkAdapter ethernetNetworkAdapter;

    Device(String deviceName, TypeofEntity typeofEntity) {
        super(typeofEntity);
        this.deviceName = deviceName;
        this.ethernetNetworkAdapter = null;
    }
}
