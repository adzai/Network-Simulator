package cz.praguecityuniversity;

public class Frame {
    private final String sourceMAC;
    private final String destinationMAC;
    private Message message;

    public Frame(String sourceMAC, String destinationMAC,Message message) {
        this.sourceMAC = sourceMAC;
        this.destinationMAC = destinationMAC;
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
