package cz.praguecityuniversity;

abstract public class Entity implements EventHandler {
    TypeofEntity typeofEntity;

    public Entity(TypeofEntity typeofEntity) {
        this.typeofEntity = typeofEntity;
    }
}

enum TypeofEntity {
    COMPUTER,
    ROUTER,
    NETWORKADAPTER,
    SWITCH,
    CONNECTION
}