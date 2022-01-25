package cz.praguecityuniversity;

enum TypeOfConnection {
    WIRELESS,
    ETHERNET
}

public class Event {
    private String eventName;
    private int startingTime;
    private Frame frame;
    private Entity entity;
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

    public int getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(int startingTime) {
        this.startingTime = startingTime;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }
}