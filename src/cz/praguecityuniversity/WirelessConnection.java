package cz.praguecityuniversity;

public class WirelessConnection extends Connection{
    public WirelessConnection() {
        super();
    }

    @Override
    public Event handleEvent(Event event, EventLogger logger) {
        return event;
    }
}
