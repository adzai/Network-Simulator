package cz.praguecityuniversity;

public class Message {
    private IPAddress sourceIP;
    private IPAddress destinationIP;
    private final String data;
    private TypeOfMessage typeOfMessage;

    public Message(IPAddress sourceIP, IPAddress destinationIP, String data, cz.praguecityuniversity.TypeOfMessage typeOfMessage) {
        this.sourceIP = sourceIP;
        this.destinationIP = destinationIP;
        this.data = data;
        this.typeOfMessage = typeOfMessage;
    }

    public IPAddress getSourceIP() {
        return sourceIP;
    }

    public void setSourceIP(IPAddress IP) {
        this.sourceIP = IP;
    }

    public IPAddress getDestinationIP() {
        return destinationIP;
    }

    public void setDestinationIP(IPAddress IP) {
        this.destinationIP = IP;
    }

    public String getData() {
        return data;
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