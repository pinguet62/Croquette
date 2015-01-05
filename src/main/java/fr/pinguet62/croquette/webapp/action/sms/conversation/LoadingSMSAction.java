package fr.pinguet62.croquette.webapp.action.sms.conversation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import fr.pinguet62.croquette.commons.dto.LoadingConversationDto;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.User;

/**
 * Load old {@link Message}s of a {@link Conversation}.
 *
 * @see LoadedSMSAction
 * @see LoadingConversationDto
 */
public final class LoadingSMSAction implements IAction {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(LoadingSMSAction.class);

    private final Conversation conversation;

    public LoadingSMSAction(Conversation conversation) {
        this.conversation = conversation;
    }

    /** Send {@link LoadingConversationDto DTO} to Smartphone. */
    @Override
    public void execute() {
        LOGGER.info("Action: " + LoadingConversationDto.KEY);

        // Convert data
        LoadingConversationDto dto = new LoadingConversationDto();
        dto.setId(conversation.getId());
        dto.setOldest(conversation.first().getId());

        // Send DTO to Smartphone
        String json = new Gson().toJson(dto);
        User.get().getXmppManager().send(json);
    }

}
