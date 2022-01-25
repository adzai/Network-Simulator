package cz.praguecityuniversity;

public class Main {

    public static void main(String[] args) {
        String configFileName;
        if (args.length == 1) {
            configFileName = args[0];
        } else {
            configFileName = "sample-config.xml";
        }
        Simulation sim = new Simulation(new EventLogger());
        ConfigurationReader configurationReader = new ConfigurationReaderXML();
        sim.loadSimulation(configFileName, configurationReader);
        sim.run();
        sim.logger.printLog();
//       sim.logger.writeToFile("test-log.txt");
    }
}
