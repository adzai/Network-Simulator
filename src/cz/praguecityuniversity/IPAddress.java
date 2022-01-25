package cz.praguecityuniversity;

/**
 * Interface allowing for multiple IP protocols.
 */
public interface IPAddress {
    String getIPAddressStr();

    String getNetworkAddressStr();

    long getIPAddressVal();

    long getNetworkAddressVal();

    int getMask();
}
