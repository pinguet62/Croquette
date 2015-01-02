package fr.pinguet62.croquette.commons.dto;

public final class LoadingConversationDto {

    public static final int COUNT = 5;

    public static final String KEY = "SMS_CONVERSATION_LOADING";

    private final String action = KEY;

    private final int count = COUNT;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOldest(Integer oldest) {
        this.oldest = oldest;
    }

}
