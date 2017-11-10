package net.mguenther.gtd.api;

import net.mguenther.gtd.domain.Item;
import net.mguenther.gtd.domain.ItemView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
@RestController
public class ItemQueryResource {

    private static final Logger log = LoggerFactory.getLogger(ItemQueryResource.class);

    private final ItemView itemView;

    @Autowired
    public ItemQueryResource(final ItemView itemView) {
        this.itemView = itemView;
    }

    @RequestMapping(path = "/items/{itemId}", method = RequestMethod.GET, produces = "application/json")
    public CompletableFuture<ResponseEntity<Item>> showItem(@PathVariable("itemId") String itemId) {

        log.info("Received a show item request for item with ID {}.", itemId);

        return itemView.getItem(itemId)
                .thenApply(optionalItem -> optionalItem.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build()))
                .exceptionally(e -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
