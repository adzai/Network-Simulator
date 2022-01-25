package cz.praguecityuniversity;

public class ConnectionDecorator extends Connection {
    protected Connection connection;

    public ConnectionDecorator(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Event handleEvent(Event event, EventLogger logger) throws EventFinished {
        return this.connection.handleEvent(event, logger);
    }
}