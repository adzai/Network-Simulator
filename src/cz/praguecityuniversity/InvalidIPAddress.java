package cz.praguecityuniversity;

public class InvalidIPAddress extends Exception {
    public InvalidIPAddress(String errorMsg) {
        super(errorMsg);
    }
}
