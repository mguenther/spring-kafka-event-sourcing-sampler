package net.mguenther.gtd.domain.event;

import java.util.Date;
import java.util.Locale;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public class DueDateAssigned extends ItemEvent {

    private final Date dueDate;

    public DueDateAssigned(final String itemId, final Date dueDate) {
        super(itemId);
        this.dueDate = dueDate;
    }

    public DueDateAssigned(final String eventId, final long timestamp, final String itemId, final Date dueDate) {
        super(eventId, timestamp, itemId);
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "DueDateAssigned{eventId=%s, itemId=%s, dueDate=%s}", getEventId(), getItemId(), getDueDate());
    }
}
