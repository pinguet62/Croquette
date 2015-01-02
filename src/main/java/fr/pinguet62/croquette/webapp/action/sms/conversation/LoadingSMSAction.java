package fr.pinguet62.croquette.webapp.action.sms.conversation;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import fr.pinguet62.croquette.commons.dto.LoadingConversationDto;
import fr.pinguet62.croquette.webapp.action.Action;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.User;

/** Load old SMS of a {@link Conversation}. */
@Action(LoadingConversationDto.KEY)
public final class LoadingSMSAction implements IAction {

    /** The {@code action} value. */
    public static final String ACTION_VALUE = "SMS_LOADING";

    /** Key for the id of {@link Conversation}. */
    public static final String CONVERSATION = "conversation";

    /** Key for number of {@link Message} to load. */
    public static final String COUNT_KEY = "count";

    /** Value for number of {@link Message} to load. */
    public static final int COUNT_VALUE = 5;

    /**
     * Key for the id of the oldest {@link Message} of the {@link Conversation}.
     */
    public static final String OLDEST = "oldest";

    /**
     * The {@link Conversation} that the {@link User} wants to load older
     * {@link Message}s.
     */
    private final Conversation conversation;

    /**
     * Constructor.
     *
     * @param conversation
     *            The {@link Conversation} that the {@link User} wants to load
     *            older {@link Message}s.
     */
    public LoadingSMSAction(Conversation conversation) {
        this.conversation = conversation;
    }

    /**
     * Create the {@link JsonObject}.<br />
     * Send to GTalk account.
     */
    @Override
    public void execute() {
        // Convert data
        LoadingConversationDto dto = new LoadingConversationDto();
        dto.setId(conversation.getId());
        dto.setOldest(conversation.first().getId());

        // Send DTO to Smartphone
        String json = new Gson().toJson(dto);
        User.get().getXmppManager().send(json);
    }

    @Override
    public boolean fromSmartphone() {
        return false;
    }

}
