package cz.praguecityuniversity;

/**
 * Thrown when the provided IP mask isn't within the
 * correct range.
 */
public class InvalidMask extends Exception {
    public InvalidMask(String errorMsg) {
        super(errorMsg);
    }
}
