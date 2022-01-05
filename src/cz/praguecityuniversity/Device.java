package cz.praguecityuniversity;


public abstract class Device extends Entity{
    String deviceName;

    Device(String deviceName, TypeofEntity typeofEntity) {
        super(typeofEntity);
        this.deviceName = deviceName;
    }
}
