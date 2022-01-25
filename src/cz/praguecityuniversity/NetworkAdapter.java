package cz.praguecityuniversity;

abstract class NetworkAdapter extends Entity {
    Device device;

    NetworkAdapter(Device device, TypeofEntity typeofEntity) {
        super(typeofEntity);
        this.device = device;
    }
}
