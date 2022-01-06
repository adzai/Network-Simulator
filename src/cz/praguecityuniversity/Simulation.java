package cz.praguecityuniversity;

import java.util.HashMap;

public class Simulation {
    private final EventScheduler eventScheduler;
    private HashMap<Integer, Entity> entities;
    Simulation() throws InvalidPortInterface, InvalidMask, InvalidIPAddress {
        this.eventScheduler = new EventScheduler();
        this.entities = new HashMap<>();
        loadFromCFG();
    }

    // TODO actually load devices and events from a config file
    void loadFromCFG() throws InvalidMask, InvalidIPAddress, InvalidPortInterface {
        initDevices();
        Event eventFromC1toC2 = new Event("event","data", TypeOfConnection.ETHERNET,1,
                new IPv4("1.0.0.1/24"), new IPv4("2.0.0.1/24"), getDevice(0));
        eventScheduler.schedule(eventFromC1toC2);
    }

    Device getDevice(Integer deviceID) {
        return (Device) entities.get(deviceID);
    }
void initDevices() throws InvalidMask, InvalidIPAddress, InvalidPortInterface {
        Computer computer1 = new Computer("Computer 1",null,TypeofEntity.COMPUTER);
        entities.put(0, computer1);
        EthernetNetworkAdapter ethernetNetworkAdapterOfComputer1 = new EthernetNetworkAdapter("EthNetAdapterComp",1,computer1,TypeofEntity.NETWORKADAPTER);
        entities.put(1, ethernetNetworkAdapterOfComputer1);
        ethernetNetworkAdapterOfComputer1.addIPAddressToPortInterface(0,new IPv4(("1.0.0.1/24")));
        computer1.ethernetNetworkAdapter = ethernetNetworkAdapterOfComputer1;

        Computer computer2 = new Computer("Computer 2",null,TypeofEntity.COMPUTER);
        entities.put(2, computer2);
        EthernetNetworkAdapter ethernetNetworkAdapterOfComputer2 = new EthernetNetworkAdapter("EthNetAdapterComp",1,computer2,TypeofEntity.NETWORKADAPTER);
        entities.put(3, ethernetNetworkAdapterOfComputer2);
        ethernetNetworkAdapterOfComputer2.addIPAddressToPortInterface(0,new IPv4(("2.0.0.1/24")));
        computer2.ethernetNetworkAdapter = ethernetNetworkAdapterOfComputer2;

        Router router = new Router("Router 1",TypeofEntity.ROUTER,null);
        entities.put(4, router);
        EthernetNetworkAdapter ethernetNetworkAdapterRouter = new EthernetNetworkAdapter("Router adapter",
                5, router,TypeofEntity.NETWORKADAPTER);
        entities.put(5, ethernetNetworkAdapterRouter);
        router.ethernetNetworkAdapter = ethernetNetworkAdapterRouter;

        ethernetNetworkAdapterRouter.addIPAddressToPortInterface(0,new IPv4("1.0.0.2/24"));
        ethernetNetworkAdapterRouter.addIPAddressToPortInterface(1,new IPv4("2.0.0.2/24"));

        router.addStaticRoute(0,new IPv4("1.0.0.0/24"));
        router.addStaticRoute(1,new IPv4("2.0.0.0/24"));


        CabledConnection cabledConnectionFromComp1ToEthAd = new CabledConnection(ethernetNetworkAdapterOfComputer1,ethernetNetworkAdapterRouter,TypeofEntity.CONNECTION);
        entities.put(6, cabledConnectionFromComp1ToEthAd);
        ethernetNetworkAdapterOfComputer1.arrayOfPortInterfaces.get(0).setConnection(cabledConnectionFromComp1ToEthAd);
        CabledConnection cabledConnectionFromRouterToComp2 = new CabledConnection(ethernetNetworkAdapterRouter,ethernetNetworkAdapterOfComputer2,TypeofEntity.CONNECTION);
        entities.put(7, cabledConnectionFromRouterToComp2);
        ethernetNetworkAdapterOfComputer2.arrayOfPortInterfaces.get(0).setConnection(cabledConnectionFromRouterToComp2);
        ethernetNetworkAdapterRouter.arrayOfPortInterfaces.get(0).setConnection(cabledConnectionFromComp1ToEthAd);
        ethernetNetworkAdapterRouter.arrayOfPortInterfaces.get(1).setConnection(cabledConnectionFromRouterToComp2);
    }

    void run() {
        System.out.println("Running the simulation");
        while (!eventScheduler.isDone()) {
            eventScheduler.next();
        }
    }
}
