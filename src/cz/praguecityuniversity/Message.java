package cz.praguecityuniversity;

public class Message {
    private final IPAddress sourceIP;
    private final IPAddress destinationIP;
    private final String data;

    public Message(IPAddress sourceIP, IPAddress destinationIP, String data) {
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
        this.data = data;
    }

    public IPAddress getSourceIP() {
        return sourceIP;
    }

    public IPAddress getDestinationIP() {
        return destinationIP;
    }

    public String getData() {
        return data;
    }
}
