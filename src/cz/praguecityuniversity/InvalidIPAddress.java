package cz.praguecityuniversity;

/**
 * Thrown when an Invalid IP address is provided to IPAddressFactory.
 */
public class InvalidIPAddress extends Exception {
    public InvalidIPAddress(String errorMsg) {
        super(errorMsg);
    }
}
