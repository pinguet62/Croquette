package fr.pinguet62.croquette.webapp.action.sms.conversation;

import com.google.gson.Gson;

import fr.pinguet62.croquette.commons.dto.LoadingConversationDto;
import fr.pinguet62.croquette.webapp.action.Action;
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
@Action(LoadingConversationDto.KEY)
public final class LoadingSMSAction implements IAction {

    private final Conversation conversation;

    public LoadingSMSAction(Conversation conversation) {
        this.conversation = conversation;
    }

    /** Send {@link LoadingConversationDto DTO} to Smartphone. */
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
