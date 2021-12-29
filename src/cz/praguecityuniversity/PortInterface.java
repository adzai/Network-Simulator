package cz.praguecityuniversity;

public class PortInterface {
    String ipAddress;
    Connection connection;

    PortInterface(String ipAddress, Connection connection) {

        this.ipAddress = ipAddress;
        this.connection = connection;
    }
}
