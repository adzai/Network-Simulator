# Network-Simulator

Network Simulator is a program written in Java programming language, which simulates the first three layers of the ISO-OSI
network model with a simple traffic simulation. The program runs based on a provided configuration (XML) file,
which contains information about the network topology, and the events that will be simulated.
The output of the simulation is the statistics of how events were handled throughout the simulation process.
The simulator is implemented for the command-line interface and is event-driven; i.e., the simulation is not running in
real-time, but it uses virtual time and events. The solution utilizes various design patterns and principles which are
implemented by using appropriate Java programming techniques. Therefore, the program provides good support for
maintenance and expandability.

For testing the implementation, an example network and events are provided in the sample-config.xml file.
The simulation was implemented for Ethernet and IPv4 protocols. Below is provided the diagram of the example network topology.

## Functionalities

* Configuration file – Network topology is created according to the specifications provided in the XML file.

* Devices - For each simulated network device it is possible to define the set of physical network interfaces
  (variable number of interfaces). The simulation allows the creation of computers, switches, and routers.
  The limit for the allowed number of interfaces for computers is 1, while for switches and routers – 5.

* Physical Layer – Classes for simulating physical layer technologies, such as cabled connection and wireless connection are defined.
  A complete implementation is provided for cabled connections, while a wireless connection is a placeholder
  that can be easily extended by adding new features.

* Link Layer – Link-layer protocol, such as Ethernet protocol is fully implemented, while Wi-Fi protocol can
  be implemented by making additions to the source code.

* Network Layer – Classes for IPv4 and IPv6 network layer protocols are defined.
  The complete implementation is provided for the IPV4 protocol. IPv6 is open for changes.

* Data corruption and dropping - All the physical connections simulate the environmental effects,
  such as data corruption and dropping due to the communication distance, electromagnetic interference, etc.
  For simulating these functionalities, corruption chance (15%) and dropping chance (15%) are defined for all the connections.

* Traffic delay - Each physical connection, device, and network adapter simulates the traffic delay for each data package sent through.

* Custom logging – The implementation provides the tools for custom logging and statistical data collection of event handling.
  The information logged for each event is the following: event name, an entity that is responsible for handling the event,
  entity from which the event came, message type, source, and destination IP addresses, and data.
  The ports that are chosen by devices based on routing are logged too.
  Additionally, information about data corruption, message dropping, or successful transmitting is provided.

## Usage
The users can test the simulation either by using the sample-config.xml file or by passing a config file name argument
to the program. If the command-line arguments is not provided, then the program uses sample-config.xml file by default.