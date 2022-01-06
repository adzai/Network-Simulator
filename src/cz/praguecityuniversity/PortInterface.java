package cz.praguecityuniversity;

public class PortInterface {
    private IPv4 ipAddress;
    private CabledConnection connection;

    public IPv4 getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(IPv4 ipAddress) {
        this.ipAddress = ipAddress;
    }

    public CabledConnection getConnection() {
        return connection;
    }

    public void setConnection(CabledConnection connection) {
        this.connection = connection;
    }
}
