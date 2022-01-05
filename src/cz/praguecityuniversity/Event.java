package cz.praguecityuniversity;

public class Event {
    String eventName;
    String data;
    TypeOfConnection connection;
    int startingTime;
    IPv4 sourceIPAddress;
    IPv4 destinationIPAddress;
    private int correctPortInterface;
    private Entity previousEntity;
    Entity entity;

    public Event(String eventName, String data, TypeOfConnection connection,
                 int startingTime, IPv4 sourceIPAddress, IPv4 destinationIPAddress, Entity entity) {
        this.eventName = eventName;
        this.data = data;
        this.connection = connection;
        this.startingTime = startingTime;
        this.sourceIPAddress = sourceIPAddress;
        this.destinationIPAddress = destinationIPAddress;
        this.entity = entity;
    }

    public void setCorrectPortInterface(int correctPortInterface) {
        if (entity.typeofEntity == TypeofEntity.ROUTER){
            this.correctPortInterface = correctPortInterface;
        }
    }

    public int getCorrectPortInterface() {
        return correctPortInterface;
    }

    public Entity getPreviousEntity() {
        return previousEntity;
    }

    public void setPreviousEntity(Entity previousEntity) {
        this.previousEntity = previousEntity;
    }
}

enum TypeOfConnection {
    WIRELESS,
    ETHERNET
}

//enum TypeOfEvent {
//    ROUTING,
//    STANDARD
//}