package fr.pinguet62.croquette.commons.dto;

public final class LoadingConversationsDto {

    public static final int COUNT = 5;

    public static final String KEY = "SMS_CONVERSATIONS_LOADING";

    private final String action = KEY;

    private final int count = COUNT;

    private Integer oldest;

    public String getAction() {
        return action;
    }

    public Integer getCount() {
        return count;
    }

    public Integer getOldest() {
        return oldest;
    }

    public void setOldest(Integer oldest) {
        this.oldest = oldest;
    }

}
