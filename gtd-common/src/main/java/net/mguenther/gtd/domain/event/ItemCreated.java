package net.mguenther.gtd.domain.event;

import java.util.Locale;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public class ItemCreated extends ItemEvent {

    private final String description;

    public ItemCreated(final String itemId, final String description) {
        super(itemId);
        this.description = description;
    }

    public ItemCreated(final String eventId, final long timestamp, final String itemId, final String description) {
        super(eventId, timestamp, itemId);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "ItemCreated{eventId=%s, itemId=%s, description=%s}", getEventId(), getItemId(), getDescription());
    }
}
