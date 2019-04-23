package fr.pinguet62.croquette.commons.dto;

import java.util.List;

public final class LoadedConversationsDto {

    public static final String KEY = "SMS_CONVERSATIONS_LOADED";

    private final String action = KEY;

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
