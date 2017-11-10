package net.mguenther.gtd.domain.commands;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
abstract public class ItemCommand {

    private final String itemId;

    public ItemCommand(final String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }
}
