package cz.praguecityuniversity;

/**
 * Creates relevant Device implementation.
 */
public class DeviceFactory {
    /**
     * Creates computer, router or switch based on passed parameters.
     * @param typeofEntity Entity type of the device to be created
     * @param deviceName Name of the device to be created
     * @param macAddress MAC address of the device to be created
     * @return Returns created device.
     * @throws NotImplemented Thrown when provided entity type does not match with the type devices implemented.
     */
    public static Device getDevice(TypeofEntity typeofEntity, String deviceName, String macAddress) throws NotImplemented {
        return switch (typeofEntity) {
            case COMPUTER, ROUTER -> new Device(deviceName, typeofEntity, macAddress, new IPProtocol(typeofEntity));
            case SWITCH -> new Device(deviceName, typeofEntity, macAddress, null);
            default -> throw new NotImplemented("Implemented only for Router, Switch and Computer");
        };
    }
}
