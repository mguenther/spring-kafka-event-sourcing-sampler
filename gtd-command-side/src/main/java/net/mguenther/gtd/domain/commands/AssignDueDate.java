package net.mguenther.gtd.domain.commands;

import java.util.Date;
import java.util.Locale;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public class AssignDueDate extends ItemCommand {

    private final Date dueDate;

    public AssignDueDate(final String itemId, final Date dueDate) {
        super(itemId);
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "AssignDueDate{id=%s, dueDate=%s}", getItemId(), getDueDate());
    }
}
