package cz.praguecityuniversity;

public class IPv6 implements IPAddress {
    IPv6() throws NotImplemented {
        throw new NotImplemented("IPv6 is not implemented");
    }
    @Override
    public String getIPAddressStr() {
        return null;
    }

    @Override
    public String getNetworkAddressStr() {
        return null;
    }

    @Override
    public long getIPAddressVal() {
        return 0;
    }

    @Override
    public long getNetworkAddressVal() {
        return 0;
    }

    @Override
    public int getMask() {
        return 0;
    }
}
