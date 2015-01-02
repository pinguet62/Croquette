package fr.pinguet62.croquette.webapp.action.sms.conversations;

import com.google.gson.Gson;

import fr.pinguet62.croquette.commons.dto.LoadingConversationsDto;
import fr.pinguet62.croquette.webapp.action.Action;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.User;

/** Load old {@link Conversation}s. */
@Action(LoadingConversationsDto.KEY)
public final class LoadingConversationsAction implements IAction {

    /** The {@code action} value. */
    public static final String ACTION_VALUE = "SMS_CONVERSATIONS_LOADING";

    /** Key for number of {@link Conversation}s to load. */
    public static final String COUNT_KEY = "count";

    /** Value for number of {@link Conversation}s to load. */
    public static final int COUNT_VALUE = 5;

    /** Key for the id of the oldest {@link Conversation}. */
    public static final String OLDEST = "oldest";

    /** Value for the id of the oldest {@link Conversation}. */
    private final Integer oldest;

    /**
     * Constructor.
     *
     * @param oldest
     *            The id of the oldest {@link Conversation}. {@code null} if no
     *            {@link Conversation} known.
     */
    public LoadingConversationsAction(Integer oldest) {
        this.oldest = oldest;
    }

    @Override
    public void execute() {
        // Convert data
        LoadingConversationsDto dto = new LoadingConversationsDto();
        dto.setOldest(oldest);

        // Send DTO to Smartphone
        String json = new Gson().toJson(dto);
        User.get().getXmppManager().send(json);
    }

    @Override
    public boolean fromSmartphone() {
        return false;
    }

}
