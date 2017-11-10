# Event Sourcing using Spring Kafka

This repository contains a sample application that demonstrates how to implement an Event-sourced systems using the CQRS architectural style. The solution uses Apache Kafka, which we easily integrate into a Spring Boot based application using Spring Kafka, Apache Avro for event serialization and deserialization and uses an in-memory H2 database that contributes to the query side of our CQRS-based system. The application itself is minimal and implements a subset of David Allen's Getting Things Done time management method.

The code presented in this repository is the joint work of [Boris Fresow](mailto://bfresow@gmail.com) and [Markus Günther](mailto://markus.guenther@gmail.com) as part of an article series on **Building Event-based applications with Spring Kafka** for the German [JavaMagazin](https://jaxenter.de/magazine/java-magazin).

## Prerequisites

Running the showcase requires a working installation of Apache ZooKeeper and Apache Kafka. We provide `Dockerfile`s for both of them to get you started easily. Please make sure that [Docker](https://docs.docker.com/engine/installation/) as well as [Docker Compose](https://docs.docker.com/compose/install/) are installed on your system.

### Versions

| Application         | Version   | Docker Image            |
| ------------------- | --------- | ----------------------- |
| Apache Kafka        | 0.11.0.0  | kafka-sampler/kafka     |
| Apache ZooKeeper    | 3.4.8-1   | kafka-sampler/zookeeper |

### Building and Running the Containers

Before you execute the code samples, make sure that you have a working environment running. If you have not done it already, use the script ```docker/build-images``` to create Docker images for all required applications. After a couple of minutes, you should be ready to go.

Once the images have been successfully built, you can start the resp. containers using the provided ```docker-compose``` script. Simply issue

```bash
$ docker-compose up
```

for starting Apache Kafka, Apache Zookeeper and Yahoo Kafka Manager. Stopping the containers is best done using a separate terminal and issueing the following commands.

```bash
$ docker-compose stop
$ docker-compose rm
```

The final ```rm``` operation deletes the containers and thus clears all state so you can start over with a clean installation.

For simplicity, we restrict the Kafka cluster to a single Kafka broker. However, scaling to more Kafka brokers is easily done via `docker-compose`. You will have to provide a sensible value for `KAFKA_ADVERTISED_HOST_NAME` (other than `localhost`) for this to work, though. 

```bash
$ docker-compose scale kafka=3   # scales up to 3 Kafka brokers
$ docker-compose scale kafka=1   # scales down to 1 Kafka broker after the previous upscale
```

After changing the number of Kafka brokers, give the cluster some time so that all brokers can finish their cluster-join procedure. This should complete in a couple of seconds and you can inspect the output of the resp. Docker containers just to be sure that everything is fine. Kafka Manager should also reflect the change in the number of Kafka brokers after they successfully joined the cluster.

## License

This work is released under the terms of the MIT license.
