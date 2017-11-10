package net.mguenther.gtd.domain;

import net.mguenther.gtd.domain.event.ItemEvent;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public interface ItemEventPublisher {

    void log(ItemEvent itemEvent);
}
