package net.mguenther.gtd.api;

import net.mguenther.gtd.domain.CommandHandler;
import net.mguenther.gtd.domain.commands.CreateItem;
import net.mguenther.gtd.domain.commands.ItemCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
@RestController
public class ItemsCommandResource {

    private static final Logger log = LoggerFactory.getLogger(ItemsCommandResource.class);

    private final CommandHandler commandHandler;

    @Autowired
    public ItemsCommandResource(final CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @RequestMapping(path = "/items", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<Object>> createItem(@RequestBody CreateItemRequest createItem) {

        log.info("Received a create item request with data {}.", createItem);

        ItemCommand createItemCommand = commandsFor(createItem);

        return commandHandler
                .onCommand(createItemCommand)
                .thenApply(dontCare -> ResponseEntity.created(itemUri(createItemCommand.getItemId())).build())
                .exceptionally(e -> {
                    log.warn("Caught an exception at the service boundary.", e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    private ItemCommand commandsFor(final CreateItemRequest createItem) {
        return new CreateItem(createItem.getDescription());
    }

    private URI itemUri(final String itemId) {
        return URI.create("/items/" + itemId);
    }
}
