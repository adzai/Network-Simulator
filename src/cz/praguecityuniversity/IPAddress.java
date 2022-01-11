package cz.praguecityuniversity;

public interface IPAddress {
    String getIPAddressStr();
    String getNetworkAddressStr();
    long getIPAddressVal();
    long getNetworkAddressVal();
    int getMask();
}
