# Event Sourcing project with SCALA
You will find in this repository a basic scala event sourcing API with kafka integrated.

## Technology-Stack

## Prerequisites

### Run Zookeeper
Use the following command from console to run zookeeper

```
zookeeper-server-start.bat C:\kafka_2.12-2.4.1\config\zookeeper.properties
```

### Run Kafka

Use the following command from console to run kafka

```
kafka-server-start.bat C:\kafka_2.12-2.4.1\config\server.properties
```

Use the following command from console to create a kafka topic

```
kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic flight-event
```

Use the following command from console to watch the kafka topic (consumer)

```
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic flight-event --from-beginning
```

To install this application, run the following commands:
```
git clone https://github.com/picpicb/FSimulator.git
cd Fsimulator
```
This will get a copy of the project installed locally. To install all of its dependencies and start each app, follow the instructions below.
use the following commands from sbt shell :
compile the code:
```
compile
```
then run the server :
```
run
```
For KafkaConsumerFlight you need to change some properties 
(  val bootstrapServer = ""
   val groupId = ""
   val topic = "")