package test.cz.praguecityuniversity;

import cz.praguecityuniversity.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class IPAddressTest {
    @Test
    public void shouldThrowIPVersionNotRecognized() {
        Exception exception = assertThrows(IPVersionNotRecognized.class, () ->
            IPAddressFactory.getIPAddress("bad ip"));
        String expectedMessage = "Couldn't recognize IP: bad ip";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    public void shouldThrowInvalidIPAddress() {
        assertThrows(InvalidIPAddress.class, () ->
                IPAddressFactory.getIPAddress("1.0.0.2")); // IP without mask
    }
    @Test
    public void shouldReturnIPv4() throws InvalidMask, InvalidIPAddress, NotImplemented, IPVersionNotRecognized {
        assertSame(IPAddressFactory.getIPAddress("1.0.0.2/24").getClass(), IPv4.class);
        assertSame(IPAddressFactory.getIPAddress("255.128.0.156/16").getClass(), IPv4.class);
        assertSame(IPAddressFactory.getIPAddress("255.128.0.156/0").getClass(), IPv4.class);
    }
    @Test
    public void shouldThrowInvalidMask() {
        assertThrows(InvalidMask.class, () ->
                IPAddressFactory.getIPAddress("1.0.0.2/46"));
        assertThrows(InvalidMask.class, () ->
                IPAddressFactory.getIPAddress("1.0.0.2/mask"));
    }
    @Test
    public void shouldThrowIPv6NotImplemented() {
        Exception exception = assertThrows(NotImplemented.class, () ->
                IPAddressFactory.getIPAddress("2001:0db8:85a3:0000:0000:8a2e:0370:7334"));
        String expectedMessage = "IPv6 is not implemented";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}