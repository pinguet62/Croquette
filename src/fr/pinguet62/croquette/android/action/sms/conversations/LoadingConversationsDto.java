package fr.pinguet62.croquette.android.action.sms.conversations;

public final class LoadingConversationsDto {

    private final String action = LoadingConversationsAction.ACTION_VALUE;

    private Integer count;

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

    public void setCount(Integer count) {
        this.count = count;
    }

    public void setOldest(Integer oldest) {
        this.oldest = oldest;
    }

}
