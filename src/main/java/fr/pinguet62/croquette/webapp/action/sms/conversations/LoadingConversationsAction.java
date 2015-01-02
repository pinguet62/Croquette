package fr.pinguet62.croquette.webapp.action.sms.conversations;

import com.google.gson.Gson;

import fr.pinguet62.croquette.commons.dto.LoadingConversationsDto;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.User;

/**
 * Load old {@link Conversation}s.
 *
 * @see LoadedConversationsAction
 * @see LoadingConversationsDto
 */
public final class LoadingConversationsAction implements IAction {

    /** The id of the oldest {@link Conversation}. */
    private final int oldest;

    /**
     * @param oldest
     *            The id of the oldest {@link Conversation}.
     */
    public LoadingConversationsAction(int oldest) {
        this.oldest = oldest;
    }

    /** Send {@link LoadingConversationsDto DTO} to Smartphone. */
    @Override
    public void execute() {
        // Convert data
        LoadingConversationsDto dto = new LoadingConversationsDto();
        dto.setOldest(oldest);

        // Send DTO to Smartphone
        String json = new Gson().toJson(dto);
        User.get().getXmppManager().send(json);
    }

}
