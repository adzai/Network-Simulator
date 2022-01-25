package cz.praguecityuniversity;

/**
 * Thrown when route is removed from the empty routing table
 */
public class EmptyRoutingTable extends Exception {
    public EmptyRoutingTable(String errorMsg) {
        super(errorMsg);
    }
}
