package cz.praguecityuniversity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EventLogger {
    private final ArrayList<String> loggedEvents = new ArrayList<>();

    public void log(Event event) {
        Message message = event.getFrame().getMessage();
        String eventString = "[" + event.getStartingTime() + "] Event name: " + event.getEventName() + ", Entity: " +
                event.getEntity().typeofEntity + " (" + event.getEntity() + ")" + ", Previous Entity: " + event.getPreviousEntity() + "\n";
        String messageString = "Message type: " + message.getTypeOfMessage() + ", Source IP: " + message.getSourceIP().getIPAddressStr() +
                ", Destination IP: " + message.getDestinationIP().getIPAddressStr() + ", Data: " + message.getData();
        loggedEvents.add(eventString + messageString);
    }

    public void logInfo(int time, String message) {
        String logString = "[" + time + "] [INFO] " + message;
        loggedEvents.add(logString);
    }

    public void printLog() {
        for (String entry : loggedEvents) {
            System.out.println(entry);
        }
    }

    public void writeToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (String entry : loggedEvents) {
            writer.write(entry + "\n");
        }
        writer.close();
    }
}
