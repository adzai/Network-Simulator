package cz.praguecityuniversity;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Provides basic logging utilities for events.
 */
public class EventLogger {
    private final ArrayList<String> loggedEvents = new ArrayList<>();

    /**
     * Logs generic information about the event.
     * @param event Event to be logged.
     */
    public void log(Event event) {
        Message message = event.getFrame().getMessage();
        String eventString = "[" + event.getStartingTime() + "] Event name: " + event.getEventName() + ", Entity: " +
                event.getEntity().typeofEntity + " (" + event.getEntity() + ")" + ", Previous Entity: " + event.getPreviousEntity() + "\n";
        String messageString = "Message type: " + message.getTypeOfMessage() + ", Source IP: " + message.getSourceIP().getIPAddressStr() +
                ", Destination IP: " + message.getDestinationIP().getIPAddressStr() + ", Data: " + message.getData();
        loggedEvents.add(eventString + messageString);
    }

    /**
     * Logs user provided message at given time.
     * @param time Time when the event to log occurred.
     * @param message User message.
     */
    public void logInfo(int time, String message) {
        String logString = "[" + time + "] [INFO] " + message;
        loggedEvents.add(logString);
    }

    /**
     * Prints the logged events to stdout.
     */
    public void printLog() {
        for (String entry : loggedEvents) {
            System.out.println(entry);
        }
    }

    /**
     * Writes logged events to the provided file.
     * @param fileName Path to file.
     * @throws IOException Thrown when the file cannot be created.
     */
    public void writeToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (String entry : loggedEvents) {
            writer.write(entry + "\n");
        }
        writer.close();
    }
}