package net.mguenther.gtd.domain;

import net.mguenther.gtd.domain.commands.ItemCommand;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public interface CommandHandler {

    CompletableFuture<Void> onCommand(ItemCommand command);
    CompletableFuture<Void> onCommand(List<ItemCommand> command);
}
