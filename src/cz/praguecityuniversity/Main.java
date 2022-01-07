package cz.praguecityuniversity;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws InvalidIPAddress, InvalidMask, InvalidPortInterface, ParserConfigurationException, IOException, SAXException {
       Simulation sim = new Simulation();
       sim.loadFromCFG("sample-config.xml");
       sim.run();
    }
}
