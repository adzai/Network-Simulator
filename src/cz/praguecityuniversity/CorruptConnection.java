package cz.praguecityuniversity;

/**
 * Corrupts the message going through a connection.
 */
public class CorruptConnection extends ConnectionDecorator {
    public CorruptConnection(Connection connection) {
        super(connection);
    }

    /**
     * Sets the message data to "CORRUPTED".
     * @param event Current event to be processed.
     * @param logger Logger that can be used to provide additional information.
     * @return Returns the event with corrupted data.
     * @throws EventFinished Thrown when this is the last event to be processed.
     */
    @Override
    public Event handleEvent(Event event, EventLogger logger) throws EventFinished {
        Event processedEvent = super.handleEvent(event, logger);
        processedEvent.getFrame().getMessage().setData("CORRUPTED");
        return processedEvent;
    }
}
