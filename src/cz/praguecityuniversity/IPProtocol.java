package cz.praguecityuniversity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Responsible for routing events
 */

public class IPProtocol {
    HashMap<IPAddress, Integer> routingTable;
    TypeofEntity typeofEntity;

    public IPProtocol(TypeofEntity typeofEntity) {
        this.typeofEntity = typeofEntity;
        this.createRoutingTable();
    }

    /**
     * Defines routing table as an empty hashmap for router.
     * For computer, the routing table is set to null, i.e., it's disabled.
     */
    public void createRoutingTable() {
        if (typeofEntity == TypeofEntity.COMPUTER) {
            routingTable = null;
        } else {
            routingTable = new HashMap<>();
        }
    }

    /**
     * Adds static route to the routing table.
     * The static route is a mapping of network IP address with port interface index.
     * @param portInterfaceIndex Index of port interface from the array of port interfaces
     * @param networkIPAddress IP address of the network
     * @param ethernetNetworkAdapter Ethernet network adapter of the device
     * @throws InvalidPortInterface Thrown when the given port interface is not defined on the device.
     * @throws InvalidIPAddress Thrown when the given network IP address is already assigned to another port.
     */
    public void addStaticRoute(int portInterfaceIndex, IPAddress networkIPAddress, EthernetNetworkAdapter ethernetNetworkAdapter) throws InvalidPortInterface, InvalidIPAddress {
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

    /**
     * Removes static route from routing table.
     * @param networkIPAddress Network IP address of the route to be removed.
     * @throws InvalidIPAddress Thrown when the given network IP address is not in routing table.
     * @throws EmptyRoutingTable Thrown when the routing table is empty.
     */
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

    /**
     * Does routing and gets correct port interface index for redirecting event.
     * @param destinationIPAddress IP address of the end device
     * @param device Device on which the routing is done
     * @return Returns 0 if the device is computer,
     * otherwise returns the corresponding port interface index of the router.
     * @throws RouteNotFound Thrown when the given destination IP address is not found in the routing table.
     */
    public int getCorrectPortInterface(IPAddress destinationIPAddress, Device device) throws RouteNotFound {
        if (device.typeofEntity == TypeofEntity.COMPUTER) {
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

    /**
     * Checks if the event message is a ping or a regular message.
     * If it's a ping the message is received by the device and redirected to the source device,
     * otherwise the message is received and the event ends.
     * @param event Event
     * @param logger Logger for logging information related to event processing
     * @param device Device on which the event is processed
     * @return Returns processed event.
     * @throws EventFinished Thrown when the event is finished.
     */
    public Event processFinalEvent(Event event, EventLogger logger, Device device) throws EventFinished {
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