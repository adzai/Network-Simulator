package cz.praguecityuniversity;

import java.util.ArrayList;

public class EthernetNetworkAdapter extends NetworkAdapter {
    ArrayList<PortInterface> arrayOfPortInterfaces;

    EthernetNetworkAdapter(int numOfPortInterfaces, Device device, TypeofEntity typeofEntity) {
        super(device, typeofEntity);
        this.arrayOfPortInterfaces = new ArrayList<>();
        this.addPortInterface(numOfPortInterfaces);
    }

    void addPortInterface(int numOfPortInterfaces) {
        if (typeofEntity.equals(TypeofEntity.COMPUTER)) {
            arrayOfPortInterfaces.add(new PortInterface());
        } else {
            for (int i = 0; i < numOfPortInterfaces; i++) {
                arrayOfPortInterfaces.add(new PortInterface());
            }
        }
    }

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

