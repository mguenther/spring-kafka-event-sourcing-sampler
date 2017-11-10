package net.mguenther.gtd.kafka.serialization;

import net.mguenther.gtd.domain.event.DueDateAssigned;
import net.mguenther.gtd.domain.event.ItemConcluded;
import net.mguenther.gtd.domain.event.ItemCreated;
import net.mguenther.gtd.domain.event.ItemEvent;
import net.mguenther.gtd.domain.event.ItemMovedToList;
import net.mguenther.gtd.domain.event.RequiredTimeAssigned;
import net.mguenther.gtd.domain.event.TagAssigned;
import net.mguenther.gtd.domain.event.TagRemoved;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Date;

/**
 * Converts bidirectionally between domain events and their respective Avro representation.
 * This is a bit of a mess, but we have to cope with it due to the lack of polymorphy and
 * inheritance in Avro.
 *
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
@Component
public class ItemEventConverter {

    /**
     * Consumes a domain event of type {@code ItemEvent} and returns its corresponding
     * Avro type (cf. {@code AvroItemEvent}).
     *
     * @param event
     *      the domain event that ought to be converted
     * @return
     *      instance of {@code AvroItemEvent} that mirrors the domain event
     */
    public AvroItemEvent from(final ItemEvent event) {

        if (event instanceof ItemCreated) return from((ItemCreated) event);
        else if (event instanceof RequiredTimeAssigned) return from((RequiredTimeAssigned) event);
        else if (event instanceof DueDateAssigned) return from((DueDateAssigned) event);
        else if (event instanceof TagAssigned) return from((TagAssigned) event);
        else if (event instanceof TagRemoved) return from((TagRemoved) event);
        else if (event instanceof ItemMovedToList) return from((ItemMovedToList) event);
        else if (event instanceof ItemConcluded) return from((ItemConcluded) event);
        else throw new IllegalArgumentException("Unsupported event type " + event.getClass());
    }

    private AvroItemEvent from(final ItemCreated event) {

        final AvroItemCreated avroEvent = AvroItemCreated.newBuilder()
                .setItemId(event.getItemId())
                .setDescription(event.getDescription())
                .build();

        return wrap(event, avroEvent);
    }

    private AvroItemEvent from(final RequiredTimeAssigned event) {

        final AvroRequiredTimeAssigned avroEvent = AvroRequiredTimeAssigned.newBuilder()
                .setItemId(event.getItemId())
                .setRequiredTime(event.getRequiredTime())
                .build();

        return wrap(event, avroEvent);
    }

    private AvroItemEvent from(final DueDateAssigned event) {

        final AvroDueDateAssigned avroEvent = AvroDueDateAssigned.newBuilder()
                .setItemId(event.getItemId())
                .setDueDate(event.getDueDate().getTime())
                .build();

        return wrap(event, avroEvent);
    }

    private AvroItemEvent from(final TagAssigned event) {

        final AvroTagAssigned avroEvent = AvroTagAssigned.newBuilder()
                .setItemId(event.getItemId())
                .setTag(event.getTag())
                .build();

        return wrap(event, avroEvent);
    }

    private AvroItemEvent from(final TagRemoved event) {

        final AvroTagRemoved avroEvent = AvroTagRemoved.newBuilder()
                .setItemId(event.getItemId())
                .setTag(event.getTag())
                .build();

        return wrap(event, avroEvent);
    }

    private AvroItemEvent from(final ItemMovedToList event) {

        final AvroItemMovedToList avroEvent = AvroItemMovedToList.newBuilder()
                .setItemId(event.getItemId())
                .setList(event.getList())
                .build();

        return wrap(event, avroEvent);
    }

    private AvroItemEvent from(final ItemConcluded event) {

        final AvroItemConcluded avroEvent = AvroItemConcluded.newBuilder()
                .setItemId(event.getItemId())
                .build();

        return wrap(event, avroEvent);
    }

    private AvroItemEvent wrap(final ItemEvent event, final Object eventPayload) {

        return AvroItemEvent
                .newBuilder()
                .setEventId(event.getEventId())
                .setTimestamp(event.getTimestamp())
                .setData(eventPayload)
                .build();
    }

    /**
     * Consumes an Avro event of type {@code AvroItemEvent} and returns its corresponding
     * domain event (cf. {@code ItemEvent}).
     *
     * @param event
     *      the Avro event that ought to be converted
     * @return
     *      instance of {@code ItemEvent} that mirrros the Avro event
     */
    public ItemEvent to(final AvroItemEvent event) {

        final String eventId = String.valueOf(event.getEventId());
        final long timestamp = event.getTimestamp();

        ItemEvent domainEvent;

        if (event.getData() instanceof AvroItemCreated) {

            final AvroItemCreated payload = (AvroItemCreated) event.getData();
            domainEvent = new ItemCreated(eventId, timestamp, payload.getItemId(), payload.getDescription());

        } else if (event.getData() instanceof AvroItemConcluded) {

            final AvroItemConcluded payload = (AvroItemConcluded) event.getData();
            domainEvent = new ItemConcluded(eventId, timestamp, payload.getItemId());

        } else if (event.getData() instanceof AvroRequiredTimeAssigned) {

            final AvroRequiredTimeAssigned payload = (AvroRequiredTimeAssigned) event.getData();
            domainEvent = new RequiredTimeAssigned(eventId, timestamp, payload.getItemId(), payload.getRequiredTime());

        } else if (event.getData() instanceof AvroDueDateAssigned) {

            final AvroDueDateAssigned payload = (AvroDueDateAssigned) event.getData();
            final Date dueDate = Date.from(Instant.ofEpochMilli(payload.getDueDate()));
            domainEvent = new DueDateAssigned(eventId, timestamp, payload.getItemId(), dueDate);

        } else if (event.getData() instanceof AvroTagAssigned) {

            final AvroTagAssigned payload = (AvroTagAssigned) event.getData();
            domainEvent = new TagAssigned(eventId, timestamp, payload.getItemId(), payload.getTag());

        } else if (event.getData() instanceof AvroTagRemoved) {

            final AvroTagRemoved payload = (AvroTagRemoved) event.getData();
            domainEvent = new TagRemoved(eventId, timestamp, payload.getItemId(), payload.getTag());

        } else if (event.getData() instanceof AvroItemMovedToList) {

            final AvroItemMovedToList payload = (AvroItemMovedToList) event.getData();
            domainEvent = new ItemMovedToList(eventId, timestamp, payload.getItemId(), payload.getList());

        } else {
            throw new IllegalArgumentException("Unsupported event payload for event with ID " + eventId);
        }

        return domainEvent;
    }
}
