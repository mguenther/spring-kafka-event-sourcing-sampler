package net.mguenther.gtd.domain;

import net.mguenther.gtd.domain.event.ItemEvent;

import java.util.concurrent.CompletableFuture;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public interface EventHandler {

    CompletableFuture<Void> onEvent(ItemEvent event);
}
