package cz.praguecityuniversity;

public class Simulation {
    final EventScheduler eventScheduler;
    final EventLogger logger;

    Simulation(EventLogger logger) {
        this.logger = logger;
        this.eventScheduler = new EventScheduler(logger);
    }

    void loadSimulation(String configName, ConfigurationReader configurationReader) {
        try {
            configurationReader.readConfig(configName, this.eventScheduler);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    void run() {
        System.out.println("Running the simulation");
        while (!eventScheduler.isDone()) {
            eventScheduler.next();
        }
    }
}