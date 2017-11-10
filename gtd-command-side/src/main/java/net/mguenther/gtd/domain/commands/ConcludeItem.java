package net.mguenther.gtd.domain.commands;

import java.util.Locale;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public class ConcludeItem extends ItemCommand {

    public ConcludeItem(final String itemId) {
        super(itemId);
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "ConcludeItem{id=%s}", getItemId());
    }
}
