package cz.praguecityuniversity;

public class Packet {
    //TODO verify IP addresses
    private final IPv4 sourceIP;
    private final IPv4 destinationIP;
    private final IPv4 data;

    public Packet(IPv4 sourceIP, IPv4 destinationIP, IPv4 data) {
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
        this.data = data;
    }

    public IPv4 getSourceIP() {
        return sourceIP;
    }

    public IPv4 getDestinationIP() {
        return destinationIP;
    }

    public IPv4 getData() {
        return data;
    }
}
