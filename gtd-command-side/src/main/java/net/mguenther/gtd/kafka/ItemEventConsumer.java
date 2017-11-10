package net.mguenther.gtd.kafka;

import net.mguenther.gtd.domain.EventHandler;
import net.mguenther.gtd.domain.event.ItemEvent;
import net.mguenther.gtd.kafka.serialization.AvroItemEvent;
import net.mguenther.gtd.kafka.serialization.ItemEventConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * Consumes {@code AvroItemEvent}s that update the internal state of domain for the command-side. This is
 * essential so that the validation that a {@code CommandHandler} performs always goes against the most
 * recent state.
 *
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
@Component
public class ItemEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(ItemEventConsumer.class);

    private final ItemEventConverter converter;

    private final EventHandler eventHandler;

    @Autowired
    public ItemEventConsumer(final ItemEventConverter converter,
                             final EventHandler eventHandler) {
        this.converter = converter;
        this.eventHandler = eventHandler;
    }

    @KafkaListener(topics = "${gtd.topic}", group = "getting-things-done")
    public void consume(final AvroItemEvent itemEvent, final Acknowledgment ack) {
        final ItemEvent event = converter.to(itemEvent);
        log.debug("Received event {}. Trying to apply it to the latest state of aggregate with ID {}.", event, event.getItemId());
        try {
            eventHandler
                    .onEvent(event)
                    .thenRun(ack::acknowledge);
        } catch (Exception e) {
            // log the exception and do *not* acknowledge the event
            log.warn("Unable to apply event {} to the latest state of aggregate with ID {}.", event, event.getItemId(), e);
        }
    }
}
