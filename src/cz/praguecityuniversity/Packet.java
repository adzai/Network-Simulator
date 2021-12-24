package cz.praguecityuniversity;

public class Packet {
    //TODO verify IP addresses
    private String sourceIP;
    private String destinationIP;
    private String data;

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
