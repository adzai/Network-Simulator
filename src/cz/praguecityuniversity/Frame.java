package cz.praguecityuniversity;

public class Frame {
    private final String sourceMAC;
    private final String destinationMAC;
    Message message;

    public Frame(String sourceMAC, String destinationMAC,Message message) {
        this.sourceMAC = sourceMAC;
        this.destinationMAC = destinationMAC;
        this.message = message;
    }
}
