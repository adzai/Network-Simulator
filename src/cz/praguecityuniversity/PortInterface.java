package cz.praguecityuniversity;

import java.util.Random;

public class PortInterface {
    private IPAddress ipAddress;
    private CabledConnection connection;
    Random random = new Random();

    public IPAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(IPAddress ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Connection getConnection() {
        int corruptionChance = random.nextInt(100) + 1;
        if (corruptionChance < connection.getCorruptionChance()) {
            return new CorruptConnection(connection);
        }
        int dropChance = random.nextInt(100) + 1;
        if (dropChance < connection.getDropChance()) {
            return new DropConnection(connection);
        }
        return connection;
    }

    public void setConnection(CabledConnection connection) {
        this.connection = connection;
    }
}
