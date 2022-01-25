package cz.praguecityuniversity;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

/**
 * Interface allowing multiple implementations of reading a config file
 */
public interface ConfigurationReader {
    void readConfig(String fileName, EventScheduler eventScheduler) throws ParserConfigurationException, IOException, SAXException, InvalidPortInterface, InvalidMask, InvalidIPAddress, NotImplemented, IPVersionNotRecognized;
}
