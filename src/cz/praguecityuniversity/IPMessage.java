package cz.praguecityuniversity;

/**
 * IP message that extends an abstract class Message
 */
public class IPMessage extends Message {
    private IPAddress sourceIP;
    private IPAddress destinationIP;
    private TypeOfMessage typeOfMessage;

    public IPMessage(String data, TypeOfMessage typeOfMessage, IPAddress sourceIP, IPAddress destinationIP) {
        super(data);
        this.typeOfMessage = typeOfMessage;
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
    }

    @Override
    public IPAddress getSourceIP() {
        return sourceIP;
    }

    @Override
    public void setSourceIP(IPAddress sourceIP) {
        this.sourceIP = sourceIP;
    }

    @Override
    public IPAddress getDestinationIP() {
        return destinationIP;
    }

    @Override
    public void setDestinationIP(IPAddress destinationIP) {
        this.destinationIP = destinationIP;
    }

    public TypeOfMessage getTypeOfMessage() {
        return typeOfMessage;
    }

    public void setTypeOfMessage(TypeOfMessage typeOfMessage) {
        this.typeOfMessage = typeOfMessage;
    }
}

enum TypeOfMessage {
    PING,
    MESSAGE
}