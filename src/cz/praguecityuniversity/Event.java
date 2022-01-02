package cz.praguecityuniversity;

public class Event {
    String eventName;
    String data;
    TypeOfConnection connection;
    int startingTime;
    IPv4 sourceIPAddress;
    IPv4 destinationIPAddress;
    Device device;
    TypeOfEvent typeOfEvent;
    private int correctPortInterface;


    public Event(String eventName, String data, TypeOfConnection connection,
                 int startingTime, IPv4 sourceIPAddress, IPv4 destinationIPAddress, Device device, TypeOfEvent typeOfEvent) {
        this.eventName = eventName;
        this.data = data;
        this.connection = connection;
        this.startingTime = startingTime;
        this.sourceIPAddress = sourceIPAddress;
        this.destinationIPAddress = destinationIPAddress;
        this.device = device;
        this.typeOfEvent = typeOfEvent;
    }

    public void setCorrectPortInterface(int correctPortInterface) {
        if (typeOfEvent.equals(TypeOfEvent.ROUTING)){
            this.correctPortInterface = correctPortInterface;
        }
    }

    public int getCorrectPortInterface() {
        return correctPortInterface;
    }
}

enum TypeOfConnection {
    WIRELESS,
    ETHERNET
}

enum TypeOfEvent {
    ROUTING,
    STANDARD
}