package cz.praguecityuniversity;

import javax.sound.sampled.Port;

public class Main {

    public static void main(String[] args) {
	// Getting packet fields example
//        Packet packet = new Packet("14.12.12.12","1221121","data");
//        System.out.println(packet.getData());
//        System.out.println(packet.getDestinationIP());
//        System.out.println(packet.getSourceIP());
        CabledConnection connection = new CabledConnection();
        PortInterface[] portInterfaceArray = new PortInterface[1];
        portInterfaceArray[0] = new PortInterface("123", connection);
        EthernetNetworkAdapter ethernetNetworkAdapter = new EthernetNetworkAdapter("adapter", portInterfaceArray);
        Computer computer = new Computer("Computer 1", ethernetNetworkAdapter);
        Event event = new Event("event", "data", TypeOfConnection.ETHERNET, 1, "" +
                "111", "123123", computer);
        System.out.println(event.connection);
        computer.handleEvent(event);
        EventScheduler es = new EventScheduler();
        es.schedule(event);
    }
}
