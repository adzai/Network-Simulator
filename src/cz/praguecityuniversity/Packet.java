package cz.praguecityuniversity;

public class Packet {
    //TODO verify IP addresses
    private final String sourceIP;
    private final String destinationIP;
    private final String data;

    public Packet(String sourceIP, String destinationIP, String data) {
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
        this.data = data;
    }

    public String getSourceIP() {
        return sourceIP;
    }

    public String getDestinationIP() {
        return destinationIP;
    }

    public String getData() {
        return data;
    }
}
