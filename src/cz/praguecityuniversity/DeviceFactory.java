package cz.praguecityuniversity;

import java.util.HashMap;

public class DeviceFactory {
    public static Device getDevice(TypeofEntity typeofEntity, String deviceName, String macAddress) throws NotImplemented {
        return switch (typeofEntity) {
            case COMPUTER -> new Device(deviceName, typeofEntity, macAddress, new IPProtocol(), null);
            case ROUTER -> new Device(deviceName, typeofEntity, macAddress, new IPProtocol(), new HashMap<IPAddress, Integer>());
            case SWITCH -> new Device(deviceName, typeofEntity, macAddress, null, null);
            default -> throw new NotImplemented("Implemented only for Router, Switch and Computer");
        };
    }
}
