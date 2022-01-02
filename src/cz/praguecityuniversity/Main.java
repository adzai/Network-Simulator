package cz.praguecityuniversity;

import java.util.Objects;

public class Main {

    public static void main(String[] args) throws InvalidIPAddress, InvalidMask, RouteNotFound, InvalidPortInterface, EmptyRoutingTable {
        EthernetNetworkAdapter ethernetNetworkAdapterComputer = new EthernetNetworkAdapter("adapter", 1, null);
        Computer computer = new Computer("Computer 1", ethernetNetworkAdapterComputer);
        ethernetNetworkAdapterComputer.device = computer;
        Event event1 = new Event("event1","data", TypeOfConnection.ETHERNET,1,
                new IPv4("1.0.0.2/24"), new IPv4("1.0.0.5/24"), computer, TypeOfEvent.STANDARD);
        Event event2 = new Event("event2","data", TypeOfConnection.ETHERNET,2,
                new IPv4("1.0.0.2/24"), new IPv4("1.0.0.5/24"), computer, TypeOfEvent.STANDARD);
        Event event3 = new Event("event3","data", TypeOfConnection.ETHERNET,3,
                new IPv4("1.0.0.2/24"), new IPv4("1.0.0.5/24"), computer, TypeOfEvent.STANDARD);

        EventScheduler es = new EventScheduler();
        es.schedule(event1);
        es.schedule(event2);
        es.schedule(event3);
        System.out.println(Objects.requireNonNull(es.eventQueue.poll()).device);
        System.out.println(Objects.requireNonNull(es.eventQueue.poll()).device);
        es.next();
        System.out.println(Objects.requireNonNull(es.eventQueue.poll()).device);

        System.out.println("__________________Router");

        Router router = new Router("Router 1",null);
        EthernetNetworkAdapter ethernetNetworkAdapterRouter = new EthernetNetworkAdapter("Router adapter",
                5, null);
        router.ethernetNetworkAdapter = ethernetNetworkAdapterRouter;
        ethernetNetworkAdapterRouter.device = router;

        ethernetNetworkAdapterRouter.addIPAddressToPortInterface(0,new IPv4("1.0.0.1/24"));
        ethernetNetworkAdapterRouter.addIPAddressToPortInterface(1,new IPv4("2.0.0.1/24"));
        ethernetNetworkAdapterRouter.addIPAddressToPortInterface(0,new IPv4("3.0.0.1/24"));
        ethernetNetworkAdapterRouter.addIPAddressToPortInterface(2,new IPv4("4.0.0.1/24"));

        ethernetNetworkAdapterRouter.removeIPAddressFromPortInterface(0);

        for (PortInterface portInterface : ethernetNetworkAdapterRouter.arrayOfPortInterfaces){
            if(portInterface.getIpAddress() != null){
                System.out.println(portInterface.getIpAddress().getIPAddressStr());
            }
        }

        ethernetNetworkAdapterRouter.removePortInterface(0);
        //TODO addStaticRoute does not give exception when the port is removed because indexes of ports change.
        // Hashmap for Port Interfaces?
        router.addStaticRoute(0,new IPv4("3.0.0.0/24"));
        router.addStaticRoute(1,new IPv4("2.0.0.0/24"));
        router.removeStaticRoute(new IPv4("3.0.0.0/24"));

        for (IPv4 netAddress : router.routingTable.keySet()){
            System.out.println(netAddress.getNetworkAddressStr());
        }

        System.out.println(router.getCorrectPortInterface(new IPv4("2.0.0.5/24")));

        Event eventForRouter = new Event("event","data", TypeOfConnection.ETHERNET,1,
                new IPv4("1.0.0.2/24"), new IPv4("2.0.0.5/24"), router, TypeOfEvent.STANDARD);

        Event routingEvent = router.handleEvent(eventForRouter);
        System.out.println(routingEvent.device);
        System.out.println(routingEvent.typeOfEvent);
        System.out.println(routingEvent.startingTime);
        System.out.println(routingEvent.getCorrectPortInterface());

        Event receivedEvent = ethernetNetworkAdapterRouter.handleEvent(routingEvent);
        System.out.println(receivedEvent.device);
        System.out.println(receivedEvent.startingTime);
        System.out.println(receivedEvent.typeOfEvent);

//        IPv4 ip = new IPv4("1.0.0.1/24");
//        System.out.println(ip.getIPOctets());
//        System.out.println(ip.getNetworkOctets());
//        System.out.println(ip.getMask());
//        System.out.println(ip.getIPAddressVal());
//        System.out.println(ip.getIPAddressStr());
//        System.out.println(ip.getNetworkAddressStr());
//        System.out.println(ip.getNetworkAddressVal());
    }
}
