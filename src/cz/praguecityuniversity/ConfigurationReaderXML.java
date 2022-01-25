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

public class ConfigurationReaderXML implements ConfigurationReader {
    HashMap<String, Device> devices;

    ConfigurationReaderXML() {
        this.devices = new HashMap<>();
    }

    @Override
    public void readConfig(String fileName, EventScheduler eventScheduler) throws ParserConfigurationException, IOException, SAXException, InvalidPortInterface, InvalidMask, InvalidIPAddress, NotImplemented, IPVersionNotRecognized {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(fileName));
        doc.getDocumentElement().normalize();
        parseDevices(doc);
        parseLinks(doc);
        parseEvents(doc, eventScheduler);
    }

    void parseDevices(Document doc) throws InvalidPortInterface, InvalidMask, InvalidIPAddress, IPVersionNotRecognized, NotImplemented {
        NodeList routerList = doc.getElementsByTagName("Router");
        NodeList computerList = doc.getElementsByTagName("Computer");
        addDevices(routerList, TypeofEntity.ROUTER);
        addDevices(computerList, TypeofEntity.COMPUTER);
    }

    void addDevices(NodeList listOfDevices, TypeofEntity typeofEntity) throws InvalidPortInterface, InvalidMask, InvalidIPAddress, NotImplemented, IPVersionNotRecognized {
        for (int i = 0; i < listOfDevices.getLength(); i++) {
            Node node = listOfDevices.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                String deviceName = element.getElementsByTagName("Name").item(0).getTextContent();
                String deviceMAC = element.getElementsByTagName("MAC").item(0).getTextContent();
                Device device = DeviceFactory.getDevice(typeofEntity, deviceName, deviceMAC);
                this.addDevice(deviceMAC, device);
                int numOfInterfaces;
                if (typeofEntity == TypeofEntity.COMPUTER) {
                    numOfInterfaces = 1;
                } else {
                    numOfInterfaces = 5;
                }
                EthernetNetworkAdapter ethernetNetworkAdapter = new EthernetNetworkAdapter(
                        numOfInterfaces, device, TypeofEntity.NETWORKADAPTER);
                device.ethernetNetworkAdapter = ethernetNetworkAdapter;
                parseInterfaces(element.getElementsByTagName("Interface"), ethernetNetworkAdapter, device);
            }
        }
    }

    void parseInterfaces(NodeList interfaces, EthernetNetworkAdapter ethernetNetworkAdapter, Device device) throws InvalidMask, InvalidIPAddress, InvalidPortInterface, IPVersionNotRecognized, NotImplemented {
        for (int i = 0; i < interfaces.getLength(); i++) {
            Node node = interfaces.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                int interfaceID = Integer.parseInt(element.getAttribute("id"));
                IPAddress ip = IPAddressFactory.getIPAddress(element.getElementsByTagName("IP").item(0).getTextContent());
                ethernetNetworkAdapter.addIPAddressToPortInterface(interfaceID, ip);
                IPProtocol IPProtocol = device.getIPProtocol();
                if (device.typeofEntity == TypeofEntity.ROUTER) {
                    IPProtocol.addStaticRoute(interfaceID, IPAddressFactory.getIPAddress(ip.getNetworkAddressStr() + "/" + ip.getMask()), device.ethernetNetworkAdapter);
                }
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

    void parseEvents(Document doc, EventScheduler eventScheduler) throws InvalidMask, InvalidIPAddress, IPVersionNotRecognized, NotImplemented {
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
                Message message = new IPMessage(data, typeOfMessage, sourceIP, destIP);
                Frame frame = new Frame(sourceDeviceMAC, "FF:FF:FF:FF", message);
                int startingTime = Integer.parseInt(element.getElementsByTagName("StartingTime").item(0).getTextContent());
                Event event = new Event(eventName, frame, startingTime, this.getDevice(sourceDeviceMAC));
                eventScheduler.schedule(event);
            }
        }
    }

    Device getDevice(String deviceMAC) {
        return devices.get(deviceMAC);
    }

    void addDevice(String macAddress, Device device) {
        this.devices.put(macAddress, device);
    }
}
