package cz.praguecityuniversity;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * IPv4 implementation.
 */
public class IPv4 implements IPAddress {
    private String IPAddressStr;
    private final long IPAddressVal;
    private final String networkAddressStr;
    private final long networkAddressVal;
    private int mask;
    private final ArrayList<ArrayList<Integer>> IPOctets;
    private final ArrayList<ArrayList<Integer>> networkOctets;

    IPv4(String IPAddressStr) throws InvalidIPAddress, InvalidMask {
        parseIPv4Address(IPAddressStr);
        String[] IPAddressArr = this.IPAddressStr.split("\\.");
        this.IPOctets = parseIPOctets(IPAddressArr);
        this.networkOctets = parseNetworkOctets(this.IPOctets, this.mask);
        this.networkAddressStr = IPOctetsToString(this.networkOctets);
        this.IPAddressVal = this.parseAsVal(this.IPAddressStr);
        this.networkAddressVal = this.parseAsVal(this.networkAddressStr);
    }

    /**
     * Parses provided IP string with a mask and if it is in the correct format, extracts the
     * mask and the IP string.
     * @param IPAddressStr IP string with a mask in the format of X.X.X.X/X
     * @throws InvalidIPAddress Thrown when an invalid IP address is provided.
     * @throws InvalidMask Thrown when an invalid IP mask is provided.
     */
    private void parseIPv4Address(String IPAddressStr) throws InvalidIPAddress, InvalidMask {
        String[] split = IPAddressStr.split("/");
        if (split.length != 2) {
            throw new InvalidIPAddress("Invalid IP address, given " + IPAddressStr + ", expected format is x.x.x.x/x");
        }
        String addressStr = split[0];
        final Pattern addressPattern = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$");
        Matcher addressMatcher = addressPattern.matcher(addressStr);
        if (!addressMatcher.matches()) {
            throw new InvalidIPAddress("Invalid IP address: " + addressStr);
        }
        String maskStr = split[1];
        final Pattern maskPattern = Pattern.compile("\\b([0-9]|[12][0-9]|3[0-2])\\b");
        Matcher maskMatcher = maskPattern.matcher(maskStr);
        if (!maskMatcher.matches()) {
            throw new InvalidMask("Invalid mask: " + maskStr);
        }
        this.IPAddressStr = addressStr;
        this.mask = Integer.parseInt(maskStr);
    }

    /**
     *
     * @param IPAddressArr Array of IP string split on ".".
     * @return IP octets
     */
    private ArrayList<ArrayList<Integer>> parseIPOctets(String[] IPAddressArr) {
        ArrayList<ArrayList<Integer>> IPOctets = new ArrayList<>();
        for (String IPSliceStr : IPAddressArr) {
            ArrayList<Integer> octet = new ArrayList<>();
            String binary = Integer.toBinaryString(Integer.parseInt(IPSliceStr));
            for (int j = 0; j < binary.length(); j++) {
                octet.add(Integer.parseInt(String.valueOf(binary.charAt(j))));
            }
            while (octet.size() < 8) {
                octet.add(0, 0);
            }
            while (octet.size() > 8) {
                octet.remove(0);
            }
            IPOctets.add(octet);
        }
        return IPOctets;
    }

    /**
     *
     * @param IPOctets Array of IP octets.
     * @param mask Mask string.
     * @return Array of network octets.
     */
    private ArrayList<ArrayList<Integer>> parseNetworkOctets(ArrayList<ArrayList<Integer>> IPOctets, int mask) {
        ArrayList<ArrayList<Integer>> networkOctets = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            ArrayList<Integer> octet = IPOctets.get(i);
            ArrayList<Integer> networkOctet = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                int ipBit = octet.get(j);
                if (mask == 0) {
                    networkOctet.add(0);
                } else {
                    networkOctet.add(ipBit);
                    mask -= 1;
                }
            }
            networkOctets.add(networkOctet);
        }
        return networkOctets;
    }

    /**
     *
     * @param octets Array of IP octets.
     * @return IP string without mask.
     */
    private String IPOctetsToString(ArrayList<ArrayList<Integer>> octets) {
        ArrayList<String> octetStrings = new ArrayList<>();
        for (ArrayList<Integer> octet : octets) {
            StringBuilder octetString = new StringBuilder();
            for (Integer num : octet) {
                octetString.append(num);
            }
            octetStrings.add(Integer.toString(Integer.parseInt(String.valueOf(octetString), 2)));
        }
        return String.join(".", octetStrings);
    }

    /**
     *
     * @param IPAddressStr IP string without mask.
     * @return Integer value of an IP address.
     */
    private long parseAsVal(String IPAddressStr) {
        long IPAddressVal = 0;
        int shiftValue = 24;
        for (String IPStr : IPAddressStr.split("\\.")) {
            long num = Long.parseLong(IPStr);
            IPAddressVal += num << shiftValue;
            shiftValue -= 8;
        }
        return IPAddressVal;
    }

    public String getIPAddressStr() {
        return IPAddressStr;
    }

    public String getNetworkAddressStr() {
        return networkAddressStr;
    }

    public long getIPAddressVal() {
        return IPAddressVal;
    }

    public long getNetworkAddressVal() {
        return networkAddressVal;
    }

    public ArrayList<ArrayList<Integer>> getNetworkOctets() {
        return networkOctets;
    }

    public ArrayList<ArrayList<Integer>> getIPOctets() {
        return IPOctets;
    }

    public int getMask() {
        return mask;
    }
}