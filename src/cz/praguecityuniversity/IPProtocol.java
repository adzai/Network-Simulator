package cz.praguecityuniversity;

import java.util.ArrayList;
import java.util.HashMap;

public class IPProtocol {
    HashMap<IPAddress, Integer> routingTable;
    TypeofEntity typeofEntity;

    public IPProtocol(TypeofEntity typeofEntity){
        this.typeofEntity = typeofEntity;
        this.createRoutingTable();
    }

    public void createRoutingTable(){
        if (typeofEntity == TypeofEntity.COMPUTER){
            routingTable = null;
        }
        else {
            routingTable = new HashMap<>();
        }
    }

    public void addStaticRoute(int portInterfaceIndex, IPAddress networkIPAddress,EthernetNetworkAdapter ethernetNetworkAdapter) throws InvalidPortInterface, InvalidIPAddress {
        ArrayList<PortInterface> arrayOfPortInterfaces = ethernetNetworkAdapter.arrayOfPortInterfaces;

        if (!arrayOfPortInterfaces.isEmpty() && arrayOfPortInterfaces.get(portInterfaceIndex) != null) {
            if (!routingTable.isEmpty()) {
                for (IPAddress IPAddress : routingTable.keySet()) {
                    if (IPAddress.getNetworkAddressVal() == networkIPAddress.getNetworkAddressVal()) {
                        throw new InvalidIPAddress("The given network IP address is already assigned to another port");
                    }
                }
            }
            routingTable.put(networkIPAddress, portInterfaceIndex);
        } else {
            throw new InvalidPortInterface("Port " + portInterfaceIndex + " is not defined on the device.");
        }
    }

    public void removeStaticRoute(IPAddress networkIPAddress) throws InvalidIPAddress, EmptyRoutingTable {
        if (routingTable != null) {
            if (!routingTable.isEmpty()) {
                for (IPAddress IPAddress : routingTable.keySet()) {
                    if (IPAddress.getNetworkAddressVal() == networkIPAddress.getNetworkAddressVal()) {
                        routingTable.remove(IPAddress);
                        break;
                    }
                }
                throw new InvalidIPAddress("The IP address - " + networkIPAddress.getNetworkAddressStr() +
                        " is not in the routing table.");
            } else {
                throw new EmptyRoutingTable("The routing table is empty.");
            }
        }
    }


    public int getCorrectPortInterface(IPAddress destinationIPAddress,Device device) throws RouteNotFound {
        if (device.typeofEntity == TypeofEntity.COMPUTER){
            return 0;
        }

        int correctPortInterface = -1;

        long destNetIPAddressVal = destinationIPAddress.getNetworkAddressVal();
        if (routingTable != null) {
            for (IPAddress networkIPAddress : routingTable.keySet()) {
                long networkIPAddressVal = networkIPAddress.getNetworkAddressVal();
                if (networkIPAddressVal == destNetIPAddressVal) {
                    correctPortInterface = routingTable.get(networkIPAddress);
                }
            }
        } else {
            correctPortInterface = 0;
        }
        if (correctPortInterface == -1) {
            throw new RouteNotFound("The route to the destination IP address was not found.");
        }
        return correctPortInterface;
    }


    public Event processFinalEvent(Event event, EventLogger logger,Device device) throws EventFinished {
        if (device.ethernetNetworkAdapter.containsIP(event.getFrame().getMessage().getDestinationIP())) {
            if (event.getFrame().getMessage().getTypeOfMessage() == TypeOfMessage.PING) {
                logger.logInfo(event.getStartingTime(), device.deviceName + " received ping from IP: " + event.getFrame().getMessage().getSourceIP().getIPAddressStr());
                event.getFrame().getMessage().setTypeOfMessage(TypeOfMessage.MESSAGE);
                event.getFrame().getMessage().setDestinationIP(event.getFrame().getMessage().getSourceIP());
                event.getFrame().getMessage().setSourceIP(event.getFrame().getMessage().getDestinationIP());
            } else {
                logger.logInfo(event.getStartingTime(), device.deviceName + " received data: " + event.getFrame().getMessage().getData() + " from IP: " + event.getFrame().getMessage().getSourceIP().getIPAddressStr());
                throw new EventFinished("Event \"" + event.getEventName() + "\" finished at time: " + event.getStartingTime());
            }
        }
        return event;
    }
}