package fr.pinguet62.croquette.android.action.sms.exchange;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public final class ReceivedSmsDto {

    private final String action = ReceivedSMSAction.ACTION_VALUE;

    private String content;

    private Integer conversation;

    private Date date;

    private Integer id;

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

    public Date getDate() {
        return date;
    }

    public Integer getId() {
        return id;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
