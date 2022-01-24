package cz.praguecollege;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Switch extends Device {

    HashMap<IPAddress, macAddress> arpTable = new HashMap<>();
//    arpTable.put("IPAddress", networkIPAddress);
//    arpTable.put("macAddress", "macAddress");

    Switch(String deviceName, TypeofEntity typeofEntity, String macAddress) {
        super(deviceName,typeofEntity,macAddress);
    }

    public void addStaticARP(IPAddress networkIPAddress, macAddress macAddress) throws InvalidIPAddress, InvalidMACAddress {
        boolean IPAddressIsUsed = false;
        boolean macAddressIsUsed = false;

        if(!arpTable.isEmpty()){
            for (IPAddress IPAddress : arpTable.keySet()) {
                if (IPAddress.getNetworkAddressVal() == networkIPAddress.getNetworkAddressVal()) {
                    IPAddressIsUsed = true;
                    break;
                } else {
                    IPAddressIsUsed = false;
                }
            }

            if(IPAddressIsUsed){
                throw new InvalidIPAddress("The given network IP address is already assigned.");
            }else{
                arpTable.put(networkIPAddress, macAddress);
            }
        } else {
            arpTable.put(networkIPAddress, macAddress);
        }
    }

    public void removeStaticARP(IPAddress networkIPAddress) throws InvalidIPAddress, arpTable {
        boolean routeRemoved = false;
        if(!arpTable.isEmpty()) {
            for (IPAddress IPAddress : arpTable.keySet()) {
                if (IPAddress.getNetworkAddressVal() == networkIPAddress.getNetworkAddressVal()) {
                    arpTable.remove(IPAddress);
                    routeRemoved = true;
                    break;
                }
            }
            if(!routeRemoved){
                throw new InvalidIPAddress("The IP address - " + networkIPAddress.getNetworkAddressStr() +
                            " is not in the ARP table.");
            }
        }
        else{
            throw new arpTable("The arp table is empty.");
        }
    }
}
