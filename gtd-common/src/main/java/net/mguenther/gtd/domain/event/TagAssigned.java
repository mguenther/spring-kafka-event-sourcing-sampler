package net.mguenther.gtd.domain.event;

import java.util.Locale;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public class TagAssigned extends ItemEvent {

    private final String tag;

    public TagAssigned(final String itemId, final String tag) {
        super(itemId);
        this.tag = tag;
    }

    public TagAssigned(final String eventId, final long timestamp, final String itemId, final String tag) {
        super(eventId, timestamp, itemId);
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "TagAssigned{eventId=%s, itemId=%s, tag=%s}", getEventId(), getItemId(), getTag());
    }
}
