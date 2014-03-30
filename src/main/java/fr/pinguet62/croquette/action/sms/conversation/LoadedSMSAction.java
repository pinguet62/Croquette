package fr.pinguet62.croquette.action.sms.conversation;

import java.text.ParseException;
import java.util.List;

import javax.json.JsonObject;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.action.ActionException;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.action.JsonDateUtil;
import fr.pinguet62.croquette.bean.SmsManagedBean;
import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.model.Message.State;
import fr.pinguet62.croquette.model.User;

/** Old SMS of a {@link Conversation} received. */
@Action(LoadedSMSAction.ACTION_VALUE)
public final class LoadedSMSAction implements IAction {

    /** The {@code action} value. */
    public static final String ACTION_VALUE = "SMS_LOADED";

    /** Key for the id of {@link Conversation}. */
    public static final String CONVERSATION_ID = "id";

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
    private final JsonObject jsonMessage;

    /**
     * Constructor.
     *
     * @param jsonMessage
     *            The JSON message.
     */
    public LoadedSMSAction(JsonObject jsonMessage) {
	this.jsonMessage = jsonMessage;
    }

    /**
     * Extract the {@link Message}s from the JSON message.<br />
     * Add to {@link Conversation}.
     */
    @Override
    public void execute() {
	try {
	    Conversation conversation = User.get().getConversations()
		    .get(jsonMessage.getInt(CONVERSATION_ID));

	    // Get each message
	    List<JsonObject> jsonMessages = jsonMessage.getJsonArray(MESSAGES)
		    .getValuesAs(JsonObject.class);
	    for (JsonObject messageJson : jsonMessages) {
		Message message = new Message();
		message.setContent(messageJson.getString(MESSAGE_CONTENT));
		message.setConversation(conversation);
		message.setDate(JsonDateUtil.fromString(messageJson
			.getString(MESSAGE_DATE)));
		message.setId(messageJson.getInt(MESSAGE_ID));
		message.setRead(true);
		message.setSent(messageJson.getBoolean(MESSAGE_SENT));
		message.setState(State.OK);
		conversation.add(message);
	    }

	    // Number of message less that default count: not other messages
	    if (jsonMessages.size() < LoadingSMSAction.COUNT_VALUE)
		conversation.setHasOldMessages(false);
	} catch (ParseException e) {
	    throw new ActionException(e);
	}

	PushContext pushContext = PushContextFactory.getDefault()
		.getPushContext();
	pushContext.push(SmsManagedBean.CHANNEL, null);
    }

    @Override
    public boolean fromSmartphone() {
	return true;
    }

}
