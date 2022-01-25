package cz.praguecityuniversity;

/**
 * Network Adapter that is a component of devices' internal hardware
 */
abstract class NetworkAdapter extends Entity {
    Device device;

    NetworkAdapter(Device device, TypeofEntity typeofEntity) {
        super(typeofEntity);
        this.device = device;
    }
}
