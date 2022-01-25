package cz.praguecityuniversity;

/**
 * Thrown when IP address does not match IPv4 or IPv6 protocols.
 */
public class IPVersionNotRecognized extends Exception {
    public IPVersionNotRecognized(String errorMsg) {
        super(errorMsg);
    }
}
