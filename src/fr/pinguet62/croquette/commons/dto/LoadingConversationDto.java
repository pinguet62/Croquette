package fr.pinguet62.croquette.commons.dto;

import fr.pinguet62.croquette.android.action.sms.conversation.LoadingConversationAction;

public final class LoadingConversationDto {

    private final String action = LoadingConversationAction.ACTION_VALUE;

    private Integer count;

    private Integer id;

    private Integer oldest;

    public String getAction() {
        return action;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getId() {
        return id;
    }

    public Integer getOldest() {
        return oldest;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOldest(Integer oldest) {
        this.oldest = oldest;
    }

}
