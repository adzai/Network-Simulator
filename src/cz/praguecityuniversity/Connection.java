package cz.praguecityuniversity;

/**
 * Connection between devices.
 * Has corruptionChance attribute that can corrupt the data in a message and
 * dropChance attribute, that can drop the message altogether.
 */
abstract public class Connection extends Entity {
    private int corruptionChance = 15; // percentage chance
    private int dropChance = 15; // percentage chance

    public Connection() {
        super(TypeofEntity.CONNECTION);
    }

    public int getCorruptionChance() {
        return corruptionChance;
    }

    public void setCorruptionChance(int chance) {
        this.corruptionChance = chance;
    }

    public int getDropChance() {
        return dropChance;
    }

    public void setDropChance(int dropChance) {
        this.dropChance = dropChance;
    }
}
