package cz.praguecityuniversity;

public class PortInterface {
    private IPv4 ipAddress;
    CabledConnection connection;

    PortInterface(CabledConnection connection) {
        this.connection = connection;
    }

    public IPv4 getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(IPv4 ipAddress) {
        this.ipAddress = ipAddress;
    }
}
