package cz.praguecityuniversity;

public class DeviceFactory {
    public static Device getDevice(TypeofEntity typeofEntity, String deviceName, String macAddress) throws NotImplemented {
        return switch (typeofEntity) {
            case COMPUTER, ROUTER -> new Device(deviceName, typeofEntity, macAddress, new IPProtocol(typeofEntity));
            case SWITCH -> new Device(deviceName, typeofEntity, macAddress, null);
            default -> throw new NotImplemented("Implemented only for Router, Switch and Computer");
        };
    }
}
