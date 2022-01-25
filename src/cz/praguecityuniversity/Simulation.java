package cz.praguecityuniversity;

/**
 * Responsible for loading and running the simulation.
 */
public class Simulation {
    final EventScheduler eventScheduler;
    final EventLogger logger;

    Simulation(EventLogger logger) {
        this.logger = logger;
        this.eventScheduler = EventScheduler.getInstance(logger);
    }

    /**
     * Loads the simulation from a configuration file using the provided reader.
     * @param configName
     * @param configurationReader
     */
    void loadSimulation(String configName, ConfigurationReader configurationReader) {
        try {
            configurationReader.readConfig(configName, this.eventScheduler);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Runs the simulation until there are no scheduled actions in eventScheduler.
     */
    void run() {
        System.out.println("Running the simulation");
        while (!eventScheduler.isDone()) {
            eventScheduler.next();
        }
    }
}