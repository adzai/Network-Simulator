package cz.praguecityuniversity;

public class CorruptConnection extends ConnectionDecorator {
    public CorruptConnection(Connection connection) {
        super(connection);
    }

    @Override
    public Event handleEvent(Event event, EventLogger logger) throws EventFinished {
        Event processedEvent =  super.handleEvent(event, logger);
        processedEvent.getFrame().getMessage().setData("CORRUPTED");
        return processedEvent;
    }
}
