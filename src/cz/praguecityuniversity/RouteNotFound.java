package cz.praguecityuniversity;

/**
 * Thrown when a route to the specified destination IP address is not found.
 */
public class RouteNotFound extends Exception {
    public RouteNotFound(String errorMsg) {
        super(errorMsg);
    }
}
