# SW-architecture-availability-tactic

Implemented Heartbeat tactic, a fault detection technique where a software component periodically sends heartbeat message and another component listens. If the heartbeat fails, the originating component is assumed to have failed and a fault correction component is notified.

There are two projects, SensorInterface and PerceptionModule, between which heartbeat is implemented .
SensorInterface is responsible for collecting data from sensors, timestamping and communicating the data with other modules of the system.
PerceptionModule is responsible for deriving an environment map based on the data provided by the sensor interface.
SensorInterface has a csv file with dummy dataset which it will send to PerceptionModule. The dataset has null value at a random place that would cause the SensorInface to crash, and this failure will be detected by the perception module.

To run the application:
1. Start Java RMI - Compile PerceptionModule. Navigate to the bin directory of the PerceptionModule project from command line and run 'rmiregistry' command to start up RMI.
2. Compile and run PerceptionModule\bin\sw\arch\selfdrivingcar\lasermapper\LaserMapperServiceImpl.java
3. Compile and run SensorInterface\src\sw\arch\selfdrivingcar\laserinterface\LaserInterfaceServiceImpl.java
