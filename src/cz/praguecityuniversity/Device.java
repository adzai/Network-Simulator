package cz.praguecityuniversity;

public abstract class Device extends Entity{
    String deviceName;
    EthernetNetworkAdapter ethernetNetworkAdapter;
    String macAddress;

    Device(String deviceName, TypeofEntity typeofEntity,String macAddress) {
        super(typeofEntity);
        this.deviceName = deviceName;
        this.ethernetNetworkAdapter = null;
        this.macAddress = macAddress;
    }
}
