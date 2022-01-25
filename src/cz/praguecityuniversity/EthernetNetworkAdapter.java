package cz.praguecityuniversity;

import java.util.ArrayList;

/**
 * Ethernet Network Adapter of the Device
 */

public class EthernetNetworkAdapter extends NetworkAdapter {
    ArrayList<PortInterface> arrayOfPortInterfaces;

    EthernetNetworkAdapter(int numOfPortInterfaces, Device device, TypeofEntity typeofEntity) {
        super(device, typeofEntity);
        this.arrayOfPortInterfaces = new ArrayList<>();
        this.addPortInterface(numOfPortInterfaces);
    }

    /**
     * Adds provided number of port interfaces on the ethernet network adapter
     * @param numOfPortInterfaces Provided number of interfaces to be defined
     */
    void addPortInterface(int numOfPortInterfaces) {
        if (typeofEntity.equals(TypeofEntity.COMPUTER)) {
            arrayOfPortInterfaces.add(new PortInterface());
        } else {
            for (int i = 0; i < numOfPortInterfaces; i++) {
                arrayOfPortInterfaces.add(new PortInterface());
            }
        }
    }

    /**
     * Assigns IP address to the port interface index
     * @param portInterfaceIndex Index of port interface from the array of port interfaces
     * @param IPAddress IP address to be assigned
     * @throws InvalidPortInterface Thrown when provided port interface index is not defined on the ethernet network adapter
     * or when the port index is already assigned with another IP address
     */
    public void addIPAddressToPortInterface(int portInterfaceIndex, IPAddress IPAddress) throws InvalidPortInterface {
        if (!arrayOfPortInterfaces.isEmpty() && arrayOfPortInterfaces.get(portInterfaceIndex) != null) {
            for (PortInterface portInterface : arrayOfPortInterfaces) {
                if (portInterface.getIpAddress() != null &&
                        portInterface.getIpAddress().getNetworkAddressVal() == IPAddress.getNetworkAddressVal()) {
                    throw new InvalidPortInterface("Port " + arrayOfPortInterfaces.indexOf(portInterface)
                            + " is already assigned to the network IP address - " + IPAddress.getNetworkAddressStr());
                } else {
                    arrayOfPortInterfaces.get(portInterfaceIndex).setIpAddress(IPAddress);
                    break;
                }
            }
        } else {
            throw new InvalidPortInterface("Port " + portInterfaceIndex +
                    " is not defined on the device");
        }
    }

    /**
     * Removes assigned IP address from the port interface index
     * @param portInterfaceIndex Index of port interface from the array of port interfaces
     * @throws InvalidPortInterface Thrown when an IP address is not assigned to any of the port interfaces
     * or when the port is not defined.
     */
    public void removeIPAddressFromPortInterface(int portInterfaceIndex) throws InvalidPortInterface {
        if (!arrayOfPortInterfaces.isEmpty() && arrayOfPortInterfaces.get(portInterfaceIndex) != null) {
            if (arrayOfPortInterfaces.get(portInterfaceIndex).getIpAddress() != null) {
                arrayOfPortInterfaces.get(portInterfaceIndex).setIpAddress(null);
            } else {
                throw new InvalidPortInterface("IP Address is not assigned to the port " + portInterfaceIndex);
            }
        } else {
            throw new InvalidPortInterface("Port" + portInterfaceIndex +
                    "is not defined on the device");
        }
    }

    /**
     * Redirects event to a cabled connection through the retrieved correct port interface when the previous entity is a router or computer.
     * When the previous entity is connection, the event is sent to the device on which the ethernet network adapter is defined.
     * Also, it adds delay simulating passing of time.
     * @param event Event
     * @param logger Logger for logging information related to event handling
     * @return Returns handled event
     * @throws EventFinished Thrown when the event is finished on the device
     */
    @Override
    public Event handleEvent(Event event, EventLogger logger) {
        switch (event.getPreviousEntity().typeofEntity) {
            case ROUTER -> {
                PortInterface correctPortInterface = arrayOfPortInterfaces.get(event.getCorrectPortInterface());
                event.setStartingTime(event.getStartingTime() + 3);
                event.setEntity(correctPortInterface.getConnection());
            }
            case COMPUTER -> {
                event.setStartingTime(event.getStartingTime() + 1);
                event.setEntity(arrayOfPortInterfaces.get(0).getConnection());
            }
            case CONNECTION -> {
                event.setEntity(device);
                event.setStartingTime(event.getStartingTime() + 1);
            }
        }
        event.setPreviousEntity(this);
        return event;
    }

    public boolean containsIP(IPAddress IPAddress) {
        for (PortInterface portInterface : arrayOfPortInterfaces) {
            IPAddress portInterfaceIP = portInterface.getIpAddress();
            if (portInterfaceIP != null && portInterfaceIP.getIPAddressStr().equals(IPAddress.getIPAddressStr())) {
                return true;
            }
        }
        return false;
    }
}

