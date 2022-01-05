package cz.praguecityuniversity;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws InvalidIPAddress, InvalidMask, RouteNotFound, InvalidPortInterface, EmptyRoutingTable {
//        EthernetNetworkAdapter ethernetNetworkAdapterComputer = new EthernetNetworkAdapter("adapter", 1, null,TypeofEntity.NETWORKADAPTER);
//        Computer computer = new Computer("Computer 1", ethernetNetworkAdapterComputer,TypeofEntity.COMPUTER);
//        ethernetNetworkAdapterComputer.device = computer;
//        Event event1 = new Event("event1","data", TypeOfConnection.ETHERNET,1,
//                new IPv4("1.0.0.2/24"), new IPv4("1.0.0.5/24"), computer);
//        Event event2 = new Event("event2","data", TypeOfConnection.ETHERNET,2,
//                new IPv4("1.0.0.2/24"), new IPv4("1.0.0.5/24"), computer);
//        Event event3 = new Event("event3","data", TypeOfConnection.ETHERNET,3,
//                new IPv4("1.0.0.2/24"), new IPv4("1.0.0.5/24"), computer);
//
//        EventScheduler es = new EventScheduler();
//        es.schedule(event1);
//        es.schedule(event2);
//        es.schedule(event3);
//        System.out.println(Objects.requireNonNull(es.eventQueue.poll()).entity);
//        System.out.println(Objects.requireNonNull(es.eventQueue.poll()).entity);
//        es.next();
//        System.out.println(Objects.requireNonNull(es.eventQueue.poll()).entity);

        System.out.println("__________________Network Simulation(Computer1 to Computer 2)____________________");

        Computer computer1 = new Computer("Computer 1",null,TypeofEntity.COMPUTER);
        EthernetNetworkAdapter ethernetNetworkAdapterOfComputer1 = new EthernetNetworkAdapter("EthNetAdapterComp",1,computer1,TypeofEntity.NETWORKADAPTER);
        ethernetNetworkAdapterOfComputer1.addIPAddressToPortInterface(0,new IPv4(("1.0.0.1/24")));
        computer1.ethernetNetworkAdapter = ethernetNetworkAdapterOfComputer1;

        Computer computer2 = new Computer("Computer 2",null,TypeofEntity.COMPUTER);
        EthernetNetworkAdapter ethernetNetworkAdapterOfComputer2 = new EthernetNetworkAdapter("EthNetAdapterComp",1,computer2,TypeofEntity.NETWORKADAPTER);
        ethernetNetworkAdapterOfComputer2.addIPAddressToPortInterface(0,new IPv4(("2.0.0.1/24")));
        computer2.ethernetNetworkAdapter = ethernetNetworkAdapterOfComputer2;

        Router router = new Router("Router 1",TypeofEntity.ROUTER,null);
        EthernetNetworkAdapter ethernetNetworkAdapterRouter = new EthernetNetworkAdapter("Router adapter",
                5, router,TypeofEntity.NETWORKADAPTER);
        router.ethernetNetworkAdapter = ethernetNetworkAdapterRouter;

        ethernetNetworkAdapterRouter.addIPAddressToPortInterface(0,new IPv4("1.0.0.2/24"));
        ethernetNetworkAdapterRouter.addIPAddressToPortInterface(1,new IPv4("2.0.0.2/24"));

//        for (PortInterface portInterface : ethernetNetworkAdapterRouter.arrayOfPortInterfaces){
//            if(portInterface.getIpAddress() != null){
//                System.out.println(portInterface.getIpAddress().getIPAddressStr());
//            }
//        }

        router.addStaticRoute(0,new IPv4("1.0.0.0/24"));
        router.addStaticRoute(1,new IPv4("2.0.0.0/24"));

        CabledConnection cabledConnectionFromComp1ToEthAd = new CabledConnection(ethernetNetworkAdapterOfComputer1,ethernetNetworkAdapterRouter,TypeofEntity.CONNECTION);
        ethernetNetworkAdapterOfComputer1.arrayOfPortInterfaces.get(0).setConnection(cabledConnectionFromComp1ToEthAd);
        CabledConnection cabledConnectionFromRouterToComp2 = new CabledConnection(ethernetNetworkAdapterRouter,ethernetNetworkAdapterOfComputer2,TypeofEntity.CONNECTION);
        ethernetNetworkAdapterOfComputer2.arrayOfPortInterfaces.get(0).setConnection(cabledConnectionFromRouterToComp2);
        ethernetNetworkAdapterRouter.arrayOfPortInterfaces.get(0).setConnection(cabledConnectionFromComp1ToEthAd);
        ethernetNetworkAdapterRouter.arrayOfPortInterfaces.get(1).setConnection(cabledConnectionFromRouterToComp2);
        Event eventFromC1toC2 = new Event("event","data", TypeOfConnection.ETHERNET,1,
                new IPv4("1.0.0.1/24"), new IPv4("2.0.0.1/24"),computer1);

        Event fromCompToNetAd = computer1.handleEvent(eventFromC1toC2);
        System.out.println("From Computer To Network Adapter:");
        System.out.println(fromCompToNetAd.entity);
        System.out.println(fromCompToNetAd.startingTime);

        Event fromNetAdToCable = ethernetNetworkAdapterOfComputer1.handleEvent(fromCompToNetAd);
        System.out.println("From Network Adapter To Cable:");
        System.out.println(fromNetAdToCable.entity);
        System.out.println(fromNetAdToCable.startingTime);

        Event fromCableToNetAd = ethernetNetworkAdapterOfComputer1.arrayOfPortInterfaces.get(0).getConnection().handleEvent(fromNetAdToCable);
        System.out.println("From Cable To Network Adapter of Router:");
        System.out.println(fromCableToNetAd.entity);
        System.out.println(fromCableToNetAd.startingTime);

        Event fromNetAdToRouter = ethernetNetworkAdapterRouter.handleEvent(fromCableToNetAd);
        System.out.println("From Network Adapter of Router To Router:");
        System.out.println(fromNetAdToRouter.entity);
        System.out.println(fromNetAdToRouter.startingTime);

        Event fromRouterToNetAD = router.handleEvent(fromNetAdToRouter);
        System.out.println("From Router To NetworkAdapter of Router:");
        System.out.println(fromRouterToNetAD.entity);
        System.out.println(fromRouterToNetAD.startingTime);

        Event fromNetAdOfRouterToCable = ethernetNetworkAdapterRouter.handleEvent(fromRouterToNetAD);
        System.out.println("From NetworkAdapter of Router To Cable:");
        System.out.println(fromNetAdOfRouterToCable.entity);
        System.out.println(fromNetAdOfRouterToCable.startingTime);

        Event fromCableToNetAdOfComp2 = ethernetNetworkAdapterOfComputer2.arrayOfPortInterfaces.get(0).getConnection().handleEvent(fromNetAdOfRouterToCable);
        System.out.println("From Cable To Net Adapter of Computer 2:");
        System.out.println(fromCableToNetAdOfComp2.entity);
        System.out.println(fromCableToNetAdOfComp2.startingTime);

        Event fromNetADOfComp2ToComp2 = ethernetNetworkAdapterOfComputer2.handleEvent(fromNetAdOfRouterToCable);
        System.out.println("From Cable To Net Adapter of Computer 2:");
        System.out.println(fromNetADOfComp2ToComp2.entity);
        System.out.println(fromNetADOfComp2ToComp2.startingTime);












//        Event routingEvent = router.handleEvent(eventForRouter);
//        System.out.println(routingEvent.entity);
//        System.out.println(routingEvent.startingTime);
//        System.out.println(routingEvent.getCorrectPortInterface());
//
//        Event receivedEvent = ethernetNetworkAdapterRouter.handleEvent(routingEvent);
//        System.out.println(receivedEvent.entity);
//        System.out.println(receivedEvent.startingTime);

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
