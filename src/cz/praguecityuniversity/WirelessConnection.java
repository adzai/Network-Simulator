package cz.praguecityuniversity;

public class WirelessConnection extends Connection{
    public WirelessConnection(TypeofEntity typeofEntity) {
        super(typeofEntity);
    }

    @Override
    public Event handleEvent(Event event, EventLogger logger) {
        return event;
    }
}
