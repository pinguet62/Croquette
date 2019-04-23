package fr.pinguet62.croquette.commons.dto;

import java.util.List;

public final class LoadedConversationDto {

    public static final String KEY = "SMS_CONVERSATION_LOADED";

    private final String action = KEY;

    private Integer conversation;

    private List<MessageDto> messages;

    public String getAction() {
        return action;
    }

    public Integer getConversation() {
        return conversation;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public void setConversation(Integer conversation) {
        this.conversation = conversation;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }

}
