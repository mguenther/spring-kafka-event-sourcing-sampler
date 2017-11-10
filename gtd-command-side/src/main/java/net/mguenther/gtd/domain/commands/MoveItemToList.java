package net.mguenther.gtd.domain.commands;

import java.util.Locale;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public class MoveItemToList extends ItemCommand {

    private final String list;

    public MoveItemToList(final String itemId, final String list) {
        super(itemId);
        this.list = list;
    }

    public String getList() {
        return list;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "MoveItemToList{id=%s, list=%s}", getItemId(), getList());
    }
}
