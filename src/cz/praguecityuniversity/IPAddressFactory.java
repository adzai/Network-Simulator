package cz.praguecityuniversity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IPAddressFactory {
    public static IPAddress getIPAddress(String IPString) throws InvalidMask, InvalidIPAddress, IPVersionNotRecognized, NotImplemented {
        String IPv4String = IPString.split("/")[0];
        final Pattern IPv4Pattern = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$");
        final Pattern IPv6Pattern = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
        Matcher IPv4Matcher = IPv4Pattern.matcher(IPv4String);
        Matcher IPv6Matcher = IPv6Pattern.matcher(IPString);
        if (IPv4Matcher.matches()) {
            return new IPv4(IPString);
        } else if (IPv6Matcher.matches()) {
            return new IPv6();
        } else {
            throw new IPVersionNotRecognized("Couldn't recognize IP: " + IPString);
        }
    }
}