package cz.praguecityuniversity;

public class Event {
    String eventName;
    String data;
    TypeOfConnection connection;
    int startingTime;
    String sourceIPAddress;
    String destinationIPAddress;
    Device device;


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