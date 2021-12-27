package cz.praguecityuniversity;

public class Event {
    private String eventName;
    private String data;
    private TypeOfConnection connection;
    private int startingTime;
    private String sourceIPAddress;
    private String destinationIPAddress;
    private Device device;


    public Event(String eventName, String data, TypeOfConnection connection,
                 int startingTime, String sourceIPAddress, String destinationIPAddress, Device device) {
        this.eventName = eventName;
        this.data = data;
        this.connection = connection;
        this.startingTime = startingTime;
        this.sourceIPAddress = sourceIPAddress;
        this.destinationIPAddress = destinationIPAddress;
        this.device = device;
    }
}

enum TypeOfConnection {
    WIRELESS,
    ETHERNET
}
