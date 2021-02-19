package net.mguenther.gtd;

import feign.Feign;
import feign.Logger;
import feign.Response;
import feign.httpclient.ApacheHttpClient;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import net.mguenther.gtd.client.CreateItem;
import net.mguenther.gtd.client.GettingThingsDone;
import net.mguenther.gtd.domain.event.ItemCreated;
import net.mguenther.gtd.kafka.serialization.AvroItemEvent;
import net.mguenther.gtd.kafka.serialization.ItemEventConverter;
import net.mguenther.gtd.kafka.serialization.ItemEventDeserializer;
import net.mguenther.kafka.junit.ExternalKafkaCluster;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static net.mguenther.kafka.junit.ObserveKeyValues.on;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Disabled
public class EventPublicationTest {

    private static final String URL = "http://localhost:8765/api";

    private final ItemEventConverter converter = new ItemEventConverter();

    @Test
    public void anItemCreatedEventShouldBePublishedAfterCreatingNewItem() throws Exception {

        ExternalKafkaCluster kafka = ExternalKafkaCluster.at("http://localhost:9092");
        GettingThingsDone gtd = createGetthingThingsDoneClient();
        String itemId = extractItemId(gtd.createItem(new CreateItem("I gotta do my homework!")));

        List<AvroItemEvent> publishedEvents = kafka
                .observeValues(on("topic-getting-things-done", 1, AvroItemEvent.class)
                        .observeFor(10, TimeUnit.SECONDS)
                        .with(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ItemEventDeserializer.class)
                        .filterOnKeys(aggregateId -> aggregateId.equals(itemId)));

        ItemCreated itemCreatedEvent = publishedEvents.stream()
                .findFirst()
                .map(converter::to)
                .map(e -> (ItemCreated) e)
                .orElseThrow(AssertionError::new);

        assertThat(itemCreatedEvent.getItemId(), equalTo(itemId));
        assertThat(itemCreatedEvent.getDescription(), equalTo("I gotta do my homework!"));
    }

    private GettingThingsDone createGetthingThingsDoneClient() {
        return Feign.builder()
                .client(new ApacheHttpClient())
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(GettingThingsDone.class))
                .logLevel(Logger.Level.FULL)
                .target(GettingThingsDone.class, URL);
    }

    private String extractItemId(final Response response) {
        return response.headers()
                .get("Location")
                .stream()
                .findFirst()
                .map(s -> s.replace("/items/", ""))
                .orElseThrow(AssertionError::new);
    }
}
