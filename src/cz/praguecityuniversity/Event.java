package cz.praguecityuniversity;

enum TypeOfConnection {
    WIRELESS,
    ETHERNET
}

public class Event {
    String eventName;
    int startingTime;
    Frame frame;
    Entity entity;
    private int correctPortInterface;
    private Entity previousEntity;

    public Event(String eventName, Frame frame, int startingTime, Entity entity) {
        this.eventName = eventName;
        this.startingTime = startingTime;
        this.frame = frame;
        this.entity = entity;
    }

    public int getCorrectPortInterface() {
        return correctPortInterface;
    }

    public void setCorrectPortInterface(int correctPortInterface) {
        if (entity.typeofEntity == TypeofEntity.ROUTER) {
            this.correctPortInterface = correctPortInterface;
        }
    }

    public Entity getPreviousEntity() {
        return previousEntity;
    }

    public void setPreviousEntity(Entity previousEntity) {
        this.previousEntity = previousEntity;
    }
}