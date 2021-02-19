package net.mguenther.gtd.client;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 */
public class CreateItem {

    private final String description;

    public CreateItem(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "CreateItemRequest{" +
                "description='" + description + '\'' +
                '}';
    }
}
