package cz.praguecityuniversity;

/**
 * Abstract Class Entity that assigns type of entity to each component of the network
 */
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