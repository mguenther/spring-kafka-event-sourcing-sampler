package net.mguenther.gtd.client;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 */
public class Item {

    private String id;

    private String description;

    private int requiredTime;

    private Date dueDate;

    private List<String> tags;

    private String associatedList;

    private boolean done;

    public String getId() {
        return id;
    }

    public boolean isDone() {
        return done;
    }

    public String getDescription() {
        return description;
    }

    public int getRequiredTime() {
        return requiredTime;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    public String getAssociatedList() {
        return associatedList;
    }
}
