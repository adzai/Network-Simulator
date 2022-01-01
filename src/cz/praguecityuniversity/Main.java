package cz.praguecityuniversity;

import java.util.Objects;

public class Main {

    public static void main(String[] args) throws InvalidIPAddress, InvalidMask {
        PortInterface[] portInterfaceArray = new PortInterface[1];
        portInterfaceArray[0] = new PortInterface("123", null);
        EthernetNetworkAdapter ethernetNetworkAdapter = new EthernetNetworkAdapter("adapter", portInterfaceArray, null);
        portInterfaceArray[0].connection = new CabledConnection(ethernetNetworkAdapter);
        Computer computer = new Computer("Computer 1", ethernetNetworkAdapter);
        ethernetNetworkAdapter.device = computer;
        Event event1 = new Event("event1", "data", TypeOfConnection.ETHERNET, 1,
                new IPv4("1.0.0.2/24"), new IPv4("1.0.0.5/24"), computer);
        Event event2 = new Event("event2", "data", TypeOfConnection.ETHERNET, 2,
                new IPv4("1.0.0.3/24"), new IPv4("1.0.0.6/24"), computer);
        Event event3 = new Event("event3", "data", TypeOfConnection.ETHERNET, 3,
                new IPv4("1.0.0.4/24"), new IPv4("1.0.0.7/24"), computer);
        System.out.println(event1.connection);
        EventScheduler es = new EventScheduler();
        es.schedule(event1);
        es.schedule(event2);
        es.schedule(event3);
        System.out.println(Objects.requireNonNull(es.eventQueue.poll()).device);
        System.out.println(Objects.requireNonNull(es.eventQueue.poll()).device);
        es.next();
        System.out.println(Objects.requireNonNull(es.eventQueue.poll()).device);
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
