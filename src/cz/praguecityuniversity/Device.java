package cz.praguecityuniversity;

import javax.sound.sampled.Port;

public abstract class Device implements EventHandler{
    String deviceName;

    Device(String deviceName) {
        this.deviceName = deviceName;
    }
}
