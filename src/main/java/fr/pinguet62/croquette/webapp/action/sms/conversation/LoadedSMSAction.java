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

/** Old SMS of a {@link Conversation} received. */
@Action(LoadedConversationDto.KEY)
public final class LoadedSMSAction implements IAction {

    /** The {@code action} value. */
    public static final String ACTION_VALUE = "SMS_CONVERSATION_LOADED";

    /** Key for the id of {@link Conversation}. */
    public static final String CONVERSATION = "conversation";

    /** Key for the content of the {@link Message} of the {@link Conversation}. */
    public static final String MESSAGE_CONTENT = "content";

    /** Key for the date of {@link Message} of the {@link Conversation}. */
    public static final String MESSAGE_DATE = "date";

    /** Key for the id of the {@link Message} of the {@link Conversation}. */
    public static final String MESSAGE_ID = "id";

    /**
     * Key for boolean to define if the {@link Message} of the
     * {@link Conversation} was sent or not.
     */
    public static final String MESSAGE_SENT = "sent";

    /** Key for number of {@link Message} to load. */
    public static final String MESSAGES = "messages";

    /** The JSON message. */
    private final String json;

    /**
     * Constructor.
     *
     * @param jsonMessage
     *            The JSON message.
     */
    public LoadedSMSAction(String json) {
        this.json = json;
    }

    /**
     * Extract the {@link Message}s from the JSON message.<br />
     * Add to {@link Conversation}.
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

    @Override
    public boolean fromSmartphone() {
        return true;
    }

}
