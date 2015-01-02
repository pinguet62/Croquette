package fr.pinguet62.croquette.webapp.action.sms.conversation;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import com.google.gson.Gson;

import fr.pinguet62.croquette.commons.dto.LoadedConversationDto;
import fr.pinguet62.croquette.commons.dto.LoadingConversationDto;
import fr.pinguet62.croquette.commons.dto.MessageDto;
import fr.pinguet62.croquette.webapp.action.Action;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.bean.SmsManagedBean;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.Message.State;
import fr.pinguet62.croquette.webapp.model.User;

/**
 * Result of {@link LoadingSMSAction} action.
 *
 * @see LoadingSMSAction
 * @see LoadedConversationDto
 */
@Action(LoadedConversationDto.KEY)
public final class LoadedSMSAction implements IAction {

    private final String json;

    public LoadedSMSAction(String json) {
        this.json = json;
    }

    /**
     * <ul>
     * <li>Get (or create if no exists) {@link Conversation}</li>
     * <li>Convert {@link MessageDto} to {@link Message}</li>
     * <li>Update view</li>
     * </ul>
     */
    @Override
    public void execute() {
        LoadedConversationDto dto = new Gson().fromJson(json.toString(),
                LoadedConversationDto.class);

        Conversation conversation = User.get().getConversations()
                .get(dto.getConversation());

        // Each message
        for (MessageDto messageDto : dto.getMessages()) {
            // Build message
            Message message = new Message();
            message.setContent(messageDto.getContent());
            message.setConversation(conversation);
            message.setDate(messageDto.getDate());
            message.setId(messageDto.getId());
            message.setRead(true);
            message.setSent(messageDto.getSent());
            message.setState(State.OK);
            conversation.add(message);
        }

        // Number of message less that default count: not other messages
        if (dto.getMessages().size() < LoadingConversationDto.COUNT)
            conversation.setHasOldMessages(false);

        // Update view
        PushContext pushContext = PushContextFactory.getDefault()
                .getPushContext();
        pushContext.push(SmsManagedBean.CHANNEL, null);
    }

}
