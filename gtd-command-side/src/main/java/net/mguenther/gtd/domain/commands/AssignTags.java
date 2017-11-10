package net.mguenther.gtd.domain.commands;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 * @author Boris Fresow (bfresow@gmail.com)
 */
public class AssignTags extends ItemCommand {

    private final List<String> tags = new ArrayList<>();

    public AssignTags(final String itemId, final List<String> tags) {
        super(itemId);
        this.tags.addAll(tags);
    }

    public List<String> getTags() {
        return Collections.unmodifiableList(tags);
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "AssignTags{id=%s, tags=%s}", getItemId(), StringUtils.join(getTags(), ", "));
    }
}
