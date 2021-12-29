package cz.praguecityuniversity;


public abstract class Device implements EventHandler{
    String deviceName;

    Device(String deviceName) {
        this.deviceName = deviceName;
    }
}
