package fr.pinguet62.croquette.android.action.sms.conversations;

import java.util.List;

import com.google.gson.annotations.SerializedName;

import fr.pinguet62.croquette.android.action.sms.MessageDto;

public final class ConversationDto {

    private Integer conversation;

    private List<MessageDto> messages;

    @SerializedName("phone number")
    private String phoneNumber;

    public Integer getConversation() {
        return conversation;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setConversation(Integer conversation) {
        this.conversation = conversation;
    }

    public void setMessages(List<MessageDto> messages) {
        this.messages = messages;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
