# Metamorphosis
An usage example for Spring Kafka

## Environment setup 
Before starting, Kafka server must be configured.

Download the latest version from the official site 
https://kafka.apache.org/downloads
 
Then, unzip the content of the file.

Go to the terminal and run zookeeper first (edit the `zookeeper.properties` to change the default configuration)

```bin/zookeeper-server-start.sh config/zookeeper.properties```


After it finishes, run an instance of Kafka (similarly, `server.properties` can be edited)

```bin/kafka-server-start.sh config/server.properties```


## Running the example
Download dependencies and install jar

```mvn clean install```

Run a Consumer instance by using the proper profile

```java -jar -Dspring.profiles.active=consumer target/metamorphosis-1.0-SNAPSHOT.jar```

In a new terminal, run the Producer mode to send 100 messages
 
```java -jar -Dspring.profiles.active=producer target/metamorphosis-1.0-SNAPSHOT.jar```

Messages are processed in Consumer Profile twice: one per `@KafkaListener` because are from different consumer group 