package cz.praguecityuniversity;

public class RouteNotFound extends Exception{
    public RouteNotFound(String errorMsg) {
        super(errorMsg);
    }
}
