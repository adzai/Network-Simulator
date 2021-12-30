package cz.praguecityuniversity;

public class Main {

    public static void main(String[] args) throws InvalidIPAddress, InvalidMask {
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
        System.out.println(ip.getIPOctets());
        System.out.println(ip.getNetworkOctets());
        System.out.println(ip.getMask());
        System.out.println(ip.getIPAddressVal());
        System.out.println(ip.getIPAddressStr());
        System.out.println(ip.getNetworkAddressStr());
        System.out.println(ip.getNetworkAddressVal());
    }
}
