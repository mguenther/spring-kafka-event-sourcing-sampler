# Event Sourcing using Spring Kafka

[![Build Status](https://travis-ci.org/mguenther/spring-kafka-event-sourcing-sampler.svg?branch=master)](https://travis-ci.org/mguenther/spring-kafka-event-sourcing-sampler.svg)

This repository contains a sample application that demonstrates how to implement an Event-sourced systems using the CQRS architectural style. The solution uses Apache Kafka, which we easily integrate into a Spring Boot based application using [Spring for Apache Kafka](https://spring.io/projects/spring-kafka) (2.6.5), Apache Avro for event serialization and deserialization and uses an in-memory H2 database that contributes to the query side of our CQRS-based system. The application itself is minimal and implements a subset of David Allen's Getting Things Done time management method.

The code presented in this repository is the joint work of [Boris Fresow](mailto://bfresow@gmail.com) and [Markus Günther](mailto://markus.guenther@gmail.com) as part of an article series on **Building Event-based applications with Spring Kafka** for the German [JavaMagazin](https://jaxenter.de/magazine/java-magazin).

## Prerequisites

Running the showcase requires a working installation of Apache ZooKeeper and Apache Kafka. We provide `Dockerfile`s for both of them to get you started easily. Please make sure that [Docker](https://docs.docker.com/engine/installation/) as well as [Docker Compose](https://docs.docker.com/compose/install/) are installed on your system.

### Versions

| Application         | Version   | Docker Image            |
| ------------------- | --------- | ----------------------- |
| Apache Kafka        | 2.6.0  | wurstmeister/kafka:2.13-2.6.0     |
| Apache ZooKeeper    | 3.4.13   | wurstmeister/zookeeper |

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

## Using the API

Running the provided `docker-compose` will fire up a couple of services. First of all, Apache Kafka as well as Apache ZooKeeper, then both the command and query side of the GTD application as well as to small services for the sake of service discovery and to unify the API. The API gateway is listening at `localhost:8765`. You will have to interact with the API gateway, which takes care of the proper routing to one instance of the command or the query side of the application.

### Overview

| API Endpoint | Method | Example |
| ------------ | -------------- | ------- |
| `/items` | POST | Creates a new item. |
| `/items` | GET | Lists all items that are currently managed. |
| `/items/{itemId}` | GET | Lists the details of a specific item. |
| `/items/{itemId}` | PUT | Modifies an existing item. |
| `/items/{itemId}` | DELETE | Closes an existing item. |

The following sections will walk you through a simple example on how to use the API via cURL.

### Creating a new item

To create new item, we simply have to provide a short description of it in JSON along with the HTTP payload.

```json
{
  "description": "Go shopping"
}
```

Using cURL we can create the item:

```bash
$ curl http://localhost:8765/api/items -X POST -H "Content-Type: application/json" -d '{"description":"Go shopping"}'
```

This request will be routed to an instance of the command-side of the GTD application, where the command will be validated before the proper event will be persisted to the event log.

### Retrieving a list of all items

After creating an item, we'd like to inspect what items our GTD application currently manages. There is an HTTP endpoint for that as well. If you issue the following cURL request

```bash
$ curl http://localhost:8765/api/items
```

you see something along the lines of the following output (pretty-printed).

```json
[
  {
    "id": "07bad2d",
    "description": "Go shopping",
    "requiredTime": 0,
    "dueDate": null,
    "tags": [      
    ],
    "associatedList": null,
    "done": false
  }
]
```

This shows the item we just created in full detail.

### Retrieving a single item

We can retrieve details of a dedicated item as well. With the next cURL command, we request the item details for the item we just created (id: `07bad2d`).

```bash
$ curl http://localhost:8765/api/items/07bad2d
```

This yields the following output (pretty-printed):

```json
{
  "id": "07bad2d",
  "description": "Go shopping",
  "requiredTime": 0,
  "dueDate": null,
  "tags": [
      ],
  "associatedList": null,
  "done": false
}
```

### Modifying an existing item

Let's try to update the item and associate it with a list of tags, a required time and put it into a dedicated list. The payload for this update looks like this:

```json
{
  "tags": ["weekly"],
  "associatedList": "project",
  "requiredTime": 5
}
```

To issue the update, we simply execute the following cURL command.

```bash
$ curl http://localhost:8765/api/items/07bad2d -X PUT -H "Content-Type:application/json" -d '{"tags": ["weekly"], "associatedList":"project", "requiredTime":5}'
```

This will validate the individiual update commands extracted from the payload against the current state of the item. If the validation holds, the respective events will be emitted and the state of the item will be updated. If we look at the details of the item again using

```bash
$ curl http://localhost:8765/api/items/07bad2d
```

we see that the update has been successfully applied.

```json
{
  "id": "07bad2d",
  "description": "Go shopping",
  "requiredTime": 5,
  "dueDate": null,
  "tags": [
    "weekly"
  ],
  "associatedList": "project",
  "done": false
}
```

### Closing an item

To close an item, we issue a DELETE request via cURL.

```bash
$ curl http://localhost:8765/api/items/07bad2d -X DELETE
```

Looking again at the details of the item, we see that its `done` attribute is now `true`.

```json
{
  "id": "07bad2d",
  "description": "Go shopping",
  "requiredTime": 5,
  "dueDate": null,
  "tags": [
    "weekly"
  ],
  "associatedList": "project",
  "done": true
}
```

## License

This work is released under the terms of the MIT license.
