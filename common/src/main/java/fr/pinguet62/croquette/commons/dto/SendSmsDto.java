package fr.pinguet62.croquette.commons.dto;

import com.google.gson.annotations.SerializedName;

public final class SendSmsDto {

    public static final String KEY = "SMS_EXCHANGE_SENDIND";

    private final String action = KEY;

    private String content;

    private Integer conversation;

    @SerializedName("phone number")
    private String phoneNumber;

    public String getAction() {
        return action;
    }

    public String getContent() {
        return content;
    }

    public Integer getConversation() {
        return conversation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setConversation(Integer conversation) {
        this.conversation = conversation;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
