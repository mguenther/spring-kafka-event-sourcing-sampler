package net.mguenther.gtd.domain.event;

import java.util.Locale;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public class RequiredTimeAssigned extends ItemEvent {

    private final int requiredTime;

    public RequiredTimeAssigned(final String itemId, final int requiredTime) {
        super(itemId);
        this.requiredTime = requiredTime;
    }

    public RequiredTimeAssigned(final String eventId, final long timestamp, final String itemId, final int requiredTime) {
        super(eventId, timestamp, itemId);
        this.requiredTime = requiredTime;
    }

    public int getRequiredTime() {
        return requiredTime;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "RequiredTimeAssigned{eventId=%s, itemId=%s, requiredTime=%s}", getEventId(), getItemId(), getRequiredTime());
    }
}
