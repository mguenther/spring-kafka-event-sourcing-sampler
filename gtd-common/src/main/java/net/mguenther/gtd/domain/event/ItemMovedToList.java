package net.mguenther.gtd.domain.event;

import java.util.Locale;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public class ItemMovedToList extends ItemEvent {

    private final String list;

    public ItemMovedToList(final String itemId, final String list) {
        super(itemId);
        this.list = list;
    }

    public ItemMovedToList(final String eventId, final long timestamp, final String itemId, final String list) {
        super(eventId, timestamp, itemId);
        this.list = list;
    }

    public String getList() {
        return list;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "ItemMovedToList{eventId=%s, itemId=%s, list=%s}", getEventId(), getItemId(), getList());
    }
}
