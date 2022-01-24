package cz.praguecityuniversity;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Simulation {
    final EventScheduler eventScheduler;
    final EventLogger logger;
    HashMap<String, Device> devices;
    Simulation(EventLogger logger) {
        this.logger = logger;
        this.eventScheduler = new EventScheduler(logger);
        this.devices = new HashMap<>();
    }

    void addDevice(String macAddress, Device device) {
        this.devices.put(macAddress, device);
    }

    Device getDevice(String deviceMAC) {
        return devices.get(deviceMAC);
    }

    void run() {
        System.out.println("Running the simulation");
        while (!eventScheduler.isDone()) {
            eventScheduler.next();
        }
    }

    // TODO actually load devices and events from a config file
    void loadFromCFG(String filename) throws InvalidMask, InvalidIPAddress, InvalidPortInterface, IOException, SAXException, ParserConfigurationException, IPVersionNotRecognized, NotImplemented {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = dbf.newDocumentBuilder();

        Document doc = db.parse(new File(filename));
        doc.getDocumentElement().normalize();
        parseComputers(doc);
        parseRouters(doc);
        parseLinks(doc);
        parseEvents(doc);
    }

    void parseComputers(Document doc) throws InvalidMask, InvalidIPAddress, InvalidPortInterface, IPVersionNotRecognized, NotImplemented {
        NodeList list = doc.getElementsByTagName("Computer");
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;
                String computerName = element.getElementsByTagName("Name").item(0).getTextContent();
                String computerMAC = element.getElementsByTagName("MAC").item(0).getTextContent();
                IPAddress computerIP = IPAddressFactory.getIPAddress(element.getElementsByTagName("IP").item(0).getTextContent());
                Computer computer = new Computer(computerName,TypeofEntity.COMPUTER,computerMAC);
                this.addDevice(computerMAC, computer);
                EthernetNetworkAdapter ethernetNetworkAdapter = new EthernetNetworkAdapter(1,computer,TypeofEntity.NETWORKADAPTER);
                ethernetNetworkAdapter.addIPAddressToPortInterface(0, computerIP);
                computer.ethernetNetworkAdapter = ethernetNetworkAdapter;

            }
        }
    }

    void parseRouters(Document doc) throws InvalidPortInterface, InvalidMask, InvalidIPAddress, IPVersionNotRecognized, NotImplemented {
        NodeList list = doc.getElementsByTagName("Router");
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element = (Element) node;
                String routerName = element.getElementsByTagName("Name").item(0).getTextContent();
                String routerMAC = element.getElementsByTagName("MAC").item(0).getTextContent();
                Router router = new Router(routerName,TypeofEntity.ROUTER,routerMAC);
                this.addDevice(routerMAC, router);
                EthernetNetworkAdapter ethernetNetworkAdapter = new EthernetNetworkAdapter(
                        5, router,TypeofEntity.NETWORKADAPTER);
                router.ethernetNetworkAdapter = ethernetNetworkAdapter;
                parseInterfaces(element.getElementsByTagName("Interface"), ethernetNetworkAdapter, router);
            }
        }
    }

    void parseInterfaces(NodeList interfaces, EthernetNetworkAdapter ethernetNetworkAdapter, Router router) throws InvalidMask, InvalidIPAddress, InvalidPortInterface, IPVersionNotRecognized, NotImplemented {
        for (int i = 0; i < interfaces.getLength(); i++) {
            Node node = interfaces.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                int interfaceID = Integer.parseInt(element.getAttribute("id"));
                IPAddress ip = IPAddressFactory.getIPAddress(element.getElementsByTagName("IP").item(0).getTextContent());
                ethernetNetworkAdapter.addIPAddressToPortInterface(interfaceID, ip);
                router.addStaticRoute(interfaceID, IPAddressFactory.getIPAddress(ip.getNetworkAddressStr() + "/" + ip.getMask()));
            }
        }
    }

    void parseLinks(Document doc) {
        NodeList list = doc.getElementsByTagName("Link");
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String sourceMAC = element.getElementsByTagName("SourceMAC").item(0).getTextContent();
                int sourceInterface = Integer.parseInt(element.getElementsByTagName("SourceInterface").item(0).getTextContent());
                String destMAC = element.getElementsByTagName("DestMAC").item(0).getTextContent();
                int destInterface = Integer.parseInt(element.getElementsByTagName("DestInterface").item(0).getTextContent());
                EthernetNetworkAdapter ethernetNetworkAdapter1 = this.getDevice(sourceMAC).ethernetNetworkAdapter;
                EthernetNetworkAdapter ethernetNetworkAdapter2 = this.getDevice(destMAC).ethernetNetworkAdapter;
                CabledConnection cabledConnection = new CabledConnection(
                        ethernetNetworkAdapter1, ethernetNetworkAdapter2);
                ethernetNetworkAdapter1.arrayOfPortInterfaces.get(sourceInterface).setConnection(cabledConnection);
                ethernetNetworkAdapter2.arrayOfPortInterfaces.get(destInterface).setConnection(cabledConnection);
            }
        }
    }

    void parseEvents(Document doc) throws InvalidMask, InvalidIPAddress, IPVersionNotRecognized, NotImplemented {
        NodeList list = doc.getElementsByTagName("Event");
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String eventName = element.getElementsByTagName("Name").item(0).getTextContent();
                String data = element.getElementsByTagName("Data").item(0).getTextContent();
                String sourceDeviceMAC = element.getElementsByTagName("SourceDeviceMAC").item(0).getTextContent();
                IPAddress sourceIP = IPAddressFactory.getIPAddress(element.getElementsByTagName("SourceDeviceIP").item(0).getTextContent());
                IPAddress destIP = IPAddressFactory.getIPAddress(element.getElementsByTagName("DestDeviceIP").item(0).getTextContent());
                TypeOfMessage typeOfMessage;
                if ("ping".equals(element.getElementsByTagName("Type").item(0).getTextContent())) {
                    typeOfMessage = TypeOfMessage.PING;
                } else {
                    typeOfMessage = TypeOfMessage.MESSAGE;
                }
                Message message = new IPMessage(data,typeOfMessage,sourceIP, destIP);
                Frame frame = new Frame(sourceDeviceMAC,"FF:FF:FF:FF",message);
                int startingTime = Integer.parseInt(element.getElementsByTagName("StartingTime").item(0).getTextContent());
                Event event = new Event(eventName, frame, startingTime, this.getDevice(sourceDeviceMAC));
                this.eventScheduler.schedule(event);
            }
        }
    }
}