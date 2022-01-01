package cz.praguecityuniversity;

import java.util.ArrayList;

public class EthernetNetworkAdapter extends NetworkAdapter{
    ArrayList<PortInterface> arrayOfPortInterfaces;


    EthernetNetworkAdapter(String deviceName, int numOfPortInterfaces, Device device) {
        super(deviceName, device);
        this.arrayOfPortInterfaces = new ArrayList<>();
        this.addPortInterface(numOfPortInterfaces);
    }

    void addPortInterface(int numOfPortInterfaces){
        for (int i = 0; i < numOfPortInterfaces; i++){
            arrayOfPortInterfaces.add(new PortInterface(new CabledConnection(this)));
        }
    }

    public void addIPAddressToPortInterface (int portInterfaceIndex, IPv4 IPAddress){
        if (portInterfaceIndex >=0 && portInterfaceIndex < arrayOfPortInterfaces.size()){
            if(arrayOfPortInterfaces.get(portInterfaceIndex) == null){
                System.out.println("The given port interface index is not defined on the device.");
            }
            else {
                for(PortInterface portInterface : arrayOfPortInterfaces){
                    if(portInterface.getIpAddress() != null &&
                            portInterface.getIpAddress().getNetworkAddressVal() == IPAddress.getNetworkAddressVal()){
                        System.out.printf("Port %s is already assigned to the given network IP address.",
                                arrayOfPortInterfaces.indexOf(portInterface));
                    }
                    else {
                        arrayOfPortInterfaces.get(portInterfaceIndex).setIpAddress(IPAddress);
                        break;
                    }
                }
            }
        }
        else {
            System.out.println("The given port interface index exceeds the number of defined port interfaces.");
        }
    }

    @Override
    public Event handleEvent(Event event) {
        switch (event.typeOfEvent){
            case ROUTING:
                PortInterface correctPortInterface = arrayOfPortInterfaces.get(event.getCorrectPortInterface());
                event.startingTime += 3;
                event.typeOfEvent = TypeOfEvent.STANDARD;
//                correctPortInterface.connection.handleEvent(event);
            case STANDARD:
                event.startingTime += 1;
//                arrayOfPortInterfaces.get(0).connection.handleEvent(event);
        }
        return event;
    }
}

