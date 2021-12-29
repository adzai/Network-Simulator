package cz.praguecityuniversity;


import java.util.ArrayList;

public class IPv4 {
    String IPAddressStr;
    String networkStr;
    int mask;
    ArrayList<ArrayList<Integer>> networkOctets;
    ArrayList<ArrayList<Integer>> IPOctets;

    IPv4(String IPAddressStr) {
       String[] split = IPAddressStr.split("/");
       String IPAddress = split[0];
        this.IPAddressStr = IPAddress;
       String[] IPAddressArr = IPAddress.split("\\.");
       this.mask = Integer.parseInt(split[1]);
       IPOctets = new ArrayList<>();
        for (String IPStr : IPAddressArr) {
            ArrayList<Integer> octet = new ArrayList<>();
            String binary = Integer.toBinaryString(Integer.parseInt(IPStr));
            for (int j = 0; j < binary.length(); j++) {
                octet.add(Integer.parseInt(String.valueOf(binary.charAt(j))));
            }
            while (octet.size() < 8) {
                octet.add(0, 0);
            }
            while (octet.size() > 8) {
                octet.remove(0);
            }
            this.IPOctets.add(octet);
        }
        this.networkOctets = new ArrayList<>();
        int mask = this.mask;
            for (int i = 0; i < 4; i++) {
                ArrayList<Integer> octet = this.IPOctets.get(i);
                ArrayList<Integer> networkOctet = new ArrayList<>();
                for (int j = 0; j < 8; j++) {
                    int ipBit = octet.get(j);
                    if (mask == 0) {
                        networkOctet.add(0);
                    }
                    else {
                        networkOctet.add(ipBit);
                        mask -= 1;
                    }
                }
                this.networkOctets.add(networkOctet);
        }
        ArrayList<String> octetStrings = new ArrayList<>();
        for (ArrayList<Integer> octet: this.networkOctets) {
            StringBuilder octetString = new StringBuilder();
            for (Integer num: octet) {
                octetString.append(num);
            }
            octetStrings.add(Integer.toString(Integer.parseInt(String.valueOf(octetString), 2)));
        }
        this.networkStr = String.join(".", octetStrings);
    }
    int parseAsInt(String IPAddressStr) {
        int IPAddressInt = 0;
        int shiftValue = 24;
        for (String IPStr : IPAddressStr.split("\\.")) {
            int num = Integer.parseInt(IPStr);
            IPAddressInt += num << shiftValue;
            shiftValue -= 8;
        }
        return IPAddressInt;
    }
}