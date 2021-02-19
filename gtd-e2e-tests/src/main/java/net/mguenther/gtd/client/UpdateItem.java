package net.mguenther.gtd.client;

import java.util.List;

/**
 * @author Markus Günther (markus.guenther@gmail.com)
 */
public class UpdateItem {

    private Long dueDate;

    private Integer requiredTime;

    private List<String> tags;

    private String associatedList;

    public Long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getRequiredTime() {
        return requiredTime;
    }

    public void setRequiredTime(int requiredTime) {
        this.requiredTime = requiredTime;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAssociatedList() {
        return associatedList;
    }

    public void setAssociatedList(String associatedList) {
        this.associatedList = associatedList;
    }

    @Override
    public String toString() {
        return "UpdateItemRequest{" +
                "dueDate=" + dueDate +
                ", requiredTime=" + requiredTime +
                ", tags=" + tags +
                ", associatedList='" + associatedList + '\'' +
                '}';
    }
}
