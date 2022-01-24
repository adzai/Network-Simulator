package cz.praguecityuniversity;

import java.util.ArrayList;
import java.util.HashMap;

public class Device extends Entity{
    String deviceName;
    EthernetNetworkAdapter ethernetNetworkAdapter;
    String macAddress;
    HashMap<IPAddress, Integer> routingTable;
    private IPProtocol IPProtocol;

    Device(String deviceName, TypeofEntity typeofEntity,String macAddress, IPProtocol IPProtocol, HashMap<IPAddress, Integer> routingTable) {
        super(typeofEntity);
        this.deviceName = deviceName;
        this.ethernetNetworkAdapter = null;
        this.macAddress = macAddress;
        this.IPProtocol = IPProtocol;
        this.routingTable = routingTable;
    }

    public Event processFinalEvent(Event event, EventLogger logger) throws EventFinished {
        if (this.ethernetNetworkAdapter.containsIP(event.getFrame().getMessage().getDestinationIP())) {
            if (event.getFrame().getMessage().getTypeOfMessage() == TypeOfMessage.PING) {
                logger.logInfo(event.getStartingTime(), this.deviceName + " received ping from IP: " + event.getFrame().getMessage().getSourceIP().getIPAddressStr());
                event.getFrame().getMessage().setTypeOfMessage(TypeOfMessage.MESSAGE);
                event.getFrame().getMessage().setDestinationIP(event.getFrame().getMessage().getSourceIP());
                event.getFrame().getMessage().setSourceIP(event.getFrame().getMessage().getDestinationIP());
            } else {
                logger.logInfo(event.getStartingTime(), this.deviceName + " received data: " + event.getFrame().getMessage().getData() + " from IP: " + event.getFrame().getMessage().getSourceIP().getIPAddressStr());
                throw new EventFinished("Event \"" + event.getEventName() + "\" finished at time: " + event.getStartingTime());
            }
        }
        return event;
    }

    public void addStaticRoute(int portInterfaceIndex, IPAddress networkIPAddress) throws InvalidPortInterface, InvalidIPAddress {
        ArrayList<PortInterface> arrayOfPortInterfaces = ethernetNetworkAdapter.arrayOfPortInterfaces;
        boolean IPAddressIsUsed = false;

        if (this.routingTable != null) {
            if(!arrayOfPortInterfaces.isEmpty() && arrayOfPortInterfaces.get(portInterfaceIndex) != null) {
                if (!routingTable.isEmpty()) {
                    for (IPAddress IPAddress : routingTable.keySet()) {
                        if (IPAddress.getNetworkAddressVal() == networkIPAddress.getNetworkAddressVal()) {
                            IPAddressIsUsed = true;
                            break;
                        } else {
                            IPAddressIsUsed = false;
                        }
                    }
                    if (IPAddressIsUsed) {
                        throw new InvalidIPAddress("The given network IP address is already assigned to another port");
                    } else {
                        routingTable.put(networkIPAddress, portInterfaceIndex);
                    }
                } else {
                    routingTable.put(networkIPAddress, portInterfaceIndex);
                }
            } else {
                throw new InvalidPortInterface("Port " + portInterfaceIndex + " is not defined on the device.");
            }
        }
    }

    public void removeStaticRoute(IPAddress networkIPAddress) throws InvalidIPAddress, EmptyRoutingTable {
        boolean routeRemoved = false;
        if (routingTable != null) {
            if (!routingTable.isEmpty()) {
                for (IPAddress IPAddress : routingTable.keySet()) {
                    if (IPAddress.getNetworkAddressVal() == networkIPAddress.getNetworkAddressVal()) {
                        routingTable.remove(IPAddress);
                        routeRemoved = true;
                        break;
                    }
                }
                if (!routeRemoved) {
                    throw new InvalidIPAddress("The IP address - " + networkIPAddress.getNetworkAddressStr() +
                            " is not in the routing table.");
                }
            } else {
                throw new EmptyRoutingTable("The routing table is empty.");
            }
        }
    }


    public int getCorrectPortInterface(IPAddress destinationIPAddress) throws RouteNotFound {
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
        if (correctPortInterface == -1){
            throw new RouteNotFound("The route to the destination IP address was not found.");
        }
        return correctPortInterface;
    }
    @Override
    public Event handleEvent(Event event, EventLogger logger) throws EventFinished {
        event = this.processFinalEvent(event, logger);
        int correctPortInterface = -1;
        try {
            correctPortInterface = getCorrectPortInterface(event.getFrame().getMessage().getDestinationIP());
        } catch (RouteNotFound routeNotFound) {
            routeNotFound.printStackTrace();
        }
        event.setCorrectPortInterface(correctPortInterface);
        logger.logInfo(event.getStartingTime(), "Chosen port interface: " + event.getCorrectPortInterface());
        event.setEntity(ethernetNetworkAdapter);
        event.setStartingTime(event.getStartingTime() + 5);
        event.setPreviousEntity(this);
        return event;
    }
}