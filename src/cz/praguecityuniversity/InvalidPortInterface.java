package cz.praguecityuniversity;

/**
 * Thrown on incorrect initialization of a port interface.
 */
public class InvalidPortInterface extends Exception {
    public InvalidPortInterface(String errorMsg) {
        super(errorMsg);
    }
}

