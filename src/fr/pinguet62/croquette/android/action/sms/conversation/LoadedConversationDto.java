package fr.pinguet62.croquette.android.action.sms.conversation;

import java.util.List;

import fr.pinguet62.croquette.android.action.sms.MessageDto;

public final class LoadedConversationDto {

    private final String action = LoadedConversationAction.ACTION_VALUE;

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
