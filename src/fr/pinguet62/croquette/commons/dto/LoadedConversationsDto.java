package fr.pinguet62.croquette.commons.dto;

import java.util.List;

import fr.pinguet62.croquette.android.action.sms.conversations.LoadedConversationsAction;

public final class LoadedConversationsDto {

    private final String action = LoadedConversationsAction.ACTION_VALUE;

    private List<ConversationDto> conversations;

    public String getAction() {
        return action;
    }

    public List<ConversationDto> getConversations() {
        return conversations;
    }

    public void setConversations(List<ConversationDto> conversations) {
        this.conversations = conversations;
    }

}
