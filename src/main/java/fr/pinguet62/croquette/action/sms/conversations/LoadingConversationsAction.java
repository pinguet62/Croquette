package fr.pinguet62.croquette.action.sms.conversations;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.User;

/** Load old {@link Conversation}s. */
@Action(LoadingConversationsAction.ACTION_VALUE)
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
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder().add(
                ACTION_KEY, ACTION_VALUE);
        if (oldest != null)
            jsonObjectBuilder.add(OLDEST, oldest);
        jsonObjectBuilder.add(COUNT_KEY, COUNT_VALUE);
        JsonObject jsonObject = jsonObjectBuilder.build();
        String message = jsonObject.toString();

        User.get().getXmppManager().send(message);
    }

    @Override
    public boolean fromSmartphone() {
        return false;
    }

}
