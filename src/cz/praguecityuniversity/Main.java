package cz.praguecityuniversity;

import javax.sound.sampled.Port;

public class Main {

    public static void main(String[] args) {
        // Getting packet fields example
//        Packet packet = new Packet("14.12.12.12","1221121","data");
//        System.out.println(packet.getData());
//        System.out.println(packet.getDestinationIP());
//        System.out.println(packet.getSourceIP());
        PortInterface[] portInterfaceArray = new PortInterface[1];
        portInterfaceArray[0] = new PortInterface("123", null);
        EthernetNetworkAdapter ethernetNetworkAdapter = new EthernetNetworkAdapter("adapter", portInterfaceArray, null);
        portInterfaceArray[0].connection = new CabledConnection(ethernetNetworkAdapter);
        Computer computer = new Computer("Computer 1", ethernetNetworkAdapter);
        ethernetNetworkAdapter.device = computer;
        Event event = new Event("event", "data", TypeOfConnection.ETHERNET, 1, "" +
                "111", "123123", computer);
        System.out.println(event.connection);
        computer.handleEvent(event);
        EventScheduler es = new EventScheduler();
        es.schedule(event);
        IPv4 ip = new IPv4("1.0.0.1/24");
        System.out.println(ip.IPOctets);
        System.out.println(ip.parseAsInt(ip.IPAddressStr));
        System.out.println(ip.networkStr);
        System.out.println(ip.parseAsInt(ip.networkStr));
        System.out.println(Integer.toBinaryString(10));
    }
}
