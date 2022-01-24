package cz.praguecityuniversity;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InvalidIPAddress, InvalidMask, InvalidPortInterface, ParserConfigurationException, IOException, SAXException, IPVersionNotRecognized, NotImplemented {
       Simulation sim = new Simulation(new EventLogger());
       sim.loadFromCFG("sample-config.xml");
       sim.run();
       sim.logger.printLog();
//       sim.logger.writeToFile("test-log.txt");
    }
}
