package cz.praguecityuniversity;

import java.util.ArrayList;
import java.util.HashMap;

public class Router extends Device{
    EthernetNetworkAdapter ethernetNetworkAdapter;
    HashMap<IPv4, Integer> routingTable = new HashMap<>();

    Router(String deviceName,TypeofEntity typeofEntity, EthernetNetworkAdapter ethernetNetworkAdapter) {
        super(deviceName,typeofEntity);
        this.ethernetNetworkAdapter = ethernetNetworkAdapter;
    }

    public void addStaticRoute(int portInterfaceIndex, IPv4 networkIPAddress) throws InvalidPortInterface, InvalidIPAddress {
        ArrayList<PortInterface> arrayOfPortInterfaces = ethernetNetworkAdapter.arrayOfPortInterfaces;
        boolean IPAddressIsUsed = false;

        if(!arrayOfPortInterfaces.isEmpty() && arrayOfPortInterfaces.get(portInterfaceIndex) != null){
            if(!routingTable.isEmpty()){
                for (IPv4 IPAddress : routingTable.keySet()){
                    if (IPAddress.getNetworkAddressVal() == networkIPAddress.getNetworkAddressVal()){
                        IPAddressIsUsed = true;
                        break;
                    }
                    else {
                        IPAddressIsUsed = false;
                    }
                }
                if(IPAddressIsUsed){
                    throw new InvalidIPAddress("The given network IP address is already assigned to another port");
                }
                else {
                    routingTable.put(networkIPAddress, portInterfaceIndex);
                }
            }
            else {
                routingTable.put(networkIPAddress, portInterfaceIndex);
            }
        }
        else {
            throw new InvalidPortInterface("Port " + portInterfaceIndex + " is not defined on the device.");
        }
    }

    public void removeStaticRoute(IPv4 networkIPAddress) throws InvalidIPAddress, EmptyRoutingTable {
        boolean routeRemoved = false;
        if(!routingTable.isEmpty()) {
            for (IPv4 IPAddress : routingTable.keySet()) {
                if (IPAddress.getNetworkAddressVal() == networkIPAddress.getNetworkAddressVal()) {
                    routingTable.remove(IPAddress);
                    routeRemoved = true;
                    break;
                }
            }
            if(!routeRemoved){
                throw new InvalidIPAddress("The IP address - " + networkIPAddress.getNetworkAddressStr() +
                            " is not in the routing table.");
            }
        }
        else{
            throw new EmptyRoutingTable("The routing table is empty.");
        }
    }


    public int getCorrectPortInterface(IPv4 destinationIPAddress) throws RouteNotFound {
        int correctPortInterface = -1;

        long destNetIPAddressVal = destinationIPAddress.getNetworkAddressVal();
        for (IPv4 networkIPAddress : routingTable.keySet()){
            long networkIPAddressVal = networkIPAddress.getNetworkAddressVal();
            if (networkIPAddressVal == destNetIPAddressVal){
                correctPortInterface = routingTable.get(networkIPAddress);
            }
        }
        if (correctPortInterface == -1){
            throw new RouteNotFound("The route to the destination IP address was not found.");
        }
        return correctPortInterface;
    }

    @Override
    public Event handleEvent(Event event) {
        int correctPortInterface = -1;
        try {
            correctPortInterface = getCorrectPortInterface(event.destinationIPAddress);
        } catch (RouteNotFound routeNotFound) {
            routeNotFound.printStackTrace();
        }
        event.setCorrectPortInterface(correctPortInterface);
        event.entity = ethernetNetworkAdapter;
        event.startingTime += 5;
        event.setPreviousEntity(this);
        return event;
    }
}