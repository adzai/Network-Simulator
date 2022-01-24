package cz.praguecityuniversity;

public class DropConnection extends ConnectionDecorator {
    public DropConnection(Connection connection) {
        super(connection);
    }

    @Override
    public Event handleEvent(Event event, EventLogger logger) throws EventFinished {
        super.handleEvent(event, logger);
        logger.logInfo(event.getStartingTime(), "Event \"" + event.getEventName() + "\" DROPPED!");
        return null;
    }
}
