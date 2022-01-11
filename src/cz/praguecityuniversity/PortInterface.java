package cz.praguecityuniversity;

public class PortInterface {
    private IPAddress ipAddress;
    private CabledConnection connection;

    public IPAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(IPAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public CabledConnection getConnection() {
        return connection;
    }

    public void setConnection(CabledConnection connection) {
        this.connection = connection;
    }
}
