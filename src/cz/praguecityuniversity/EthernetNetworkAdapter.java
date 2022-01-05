package cz.praguecityuniversity;

import java.util.ArrayList;

public class EthernetNetworkAdapter extends NetworkAdapter{
    ArrayList<PortInterface> arrayOfPortInterfaces;


    EthernetNetworkAdapter(String deviceName, int numOfPortInterfaces, Device device, TypeofEntity typeofEntity) {
        super(deviceName, device, typeofEntity);
        this.arrayOfPortInterfaces = new ArrayList<>();
        this.addPortInterface(numOfPortInterfaces);
    }

    void addPortInterface(int numOfPortInterfaces){
        if(typeofEntity.equals(TypeofEntity.COMPUTER)){
            arrayOfPortInterfaces.add(new PortInterface());
        }
        else {
            for (int i = 0; i < numOfPortInterfaces; i++) {
                arrayOfPortInterfaces.add(new PortInterface());
            }
        }
    }

    public void addIPAddressToPortInterface (int portInterfaceIndex, IPv4 IPAddress) throws InvalidPortInterface {
        if(!arrayOfPortInterfaces.isEmpty() && arrayOfPortInterfaces.get(portInterfaceIndex) != null){
            for(PortInterface portInterface : arrayOfPortInterfaces){
                if(portInterface.getIpAddress() != null &&
                        portInterface.getIpAddress().getNetworkAddressVal() == IPAddress.getNetworkAddressVal()){
                    throw new InvalidPortInterface("Port " + arrayOfPortInterfaces.indexOf(portInterface)
                            + " is already assigned to the network IP address - " + IPAddress.getNetworkAddressStr());
                }
                else {
                    arrayOfPortInterfaces.get(portInterfaceIndex).setIpAddress(IPAddress);
                    break;
                }
            }
        }
        else {
            throw new InvalidPortInterface("Port " + portInterfaceIndex +
                    " is not defined on the device");
        }
    }

    public void removeIPAddressFromPortInterface (int portInterfaceIndex) throws InvalidPortInterface {
        if(!arrayOfPortInterfaces.isEmpty() && arrayOfPortInterfaces.get(portInterfaceIndex) != null){
            if(arrayOfPortInterfaces.get(portInterfaceIndex).getIpAddress() != null){
                arrayOfPortInterfaces.get(portInterfaceIndex).setIpAddress(null);
            }
            else {
                throw new InvalidPortInterface("IP Address is not assigned to the port " + portInterfaceIndex);
            }
        }
        else {
            throw new InvalidPortInterface("Port" + portInterfaceIndex +
                    "is not defined on the device");
        }
    }

    @Override
    public Event handleEvent(Event event) {
        switch (event.getPreviousEntity().typeofEntity){
            case ROUTER:
                PortInterface correctPortInterface = arrayOfPortInterfaces.get(event.getCorrectPortInterface());
                event.startingTime += 3;
                event.entity = correctPortInterface.getConnection();
                break;
            case COMPUTER:
                event.startingTime += 1;
                event.entity = arrayOfPortInterfaces.get(0).getConnection();
                break;
            case CONNECTION:
                event.entity = device;
                event.startingTime += 1;
                break;
        }
        event.setPreviousEntity(this);
        return event;
    }
}

