package fr.pinguet62.croquette.commons.dto;

public final class LoadingConversationDto {

    public static final int COUNT = 5;

    public static final String KEY = "SMS_CONVERSATION_LOADING";

    private final String action = KEY;

    private final int count = COUNT;

    private int id;

    /**
     * The ID of oldest message loaded.<br>
     * {@code null} if no message has been already loaded.
     */
    private Integer oldest;

    public String getAction() {
        return action;
    }

    public Integer getCount() {
        return count;
    }

    public int getId() {
        return id;
    }

    public Integer getOldest() {
        return oldest;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOldest(Integer oldest) {
        this.oldest = oldest;
    }

}
