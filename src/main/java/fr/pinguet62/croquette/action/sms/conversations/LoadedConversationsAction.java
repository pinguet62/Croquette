package fr.pinguet62.croquette.action.sms.conversations;

import java.text.ParseException;
import java.util.List;

import javax.json.JsonObject;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.action.ActionException;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.action.JsonDateUtil;
import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.model.Message.State;
import fr.pinguet62.croquette.model.User;

/** Old {@link Conversation}s received. */
@Action(LoadedConversationsAction.ACTION_VALUE)
public final class LoadedConversationsAction implements IAction {

    /** The {@code action} value. */
    public static final String ACTION_VALUE = "SMS_CONVERSATIONS_LOADED";

    /** Key for id of {@link Conversation}. */
    public static final String CONVERSATION_ID = "id";

    /** Key for {@link Message} of {@link Conversation}. */
    public static final String CONVERSATION_MESSAGE = "message";

    /** Key for content of {@link Message} of {@link Conversation}. */
    public static final String CONVERSATION_MESSAGE_CONTENT = "content";

    /** Key for date of {@link Message} of {@link Conversation}. */
    public static final String CONVERSATION_MESSAGE_DATE = "date";

    /** Key for id of {@link Message} of {@link Conversation}. */
    public static final String CONVERSATION_MESSAGE_ID = "id";

    /**
     * Key for boolean to define if the {@link Message} of the
     * {@link Conversation} was sent or not.
     */
    public static final String CONVERSATION_MESSAGE_SENT = "sent";

    /** Key for number of {@link Conversation} to load. */
    public static final String CONVERSATIONS = "conversations";

    /** The JSON message. */
    private final JsonObject jsonMessage;

    /**
     * Constructor.
     *
     * @param jsonMessage
     *            The JSON message.
     */
    public LoadedConversationsAction(JsonObject jsonMessage) {
	this.jsonMessage = jsonMessage;
    }

    @Override
    public void execute() {
	try {
	    // Get each conversation
	    List<JsonObject> jsonConversations = jsonMessage.getJsonArray(
		    CONVERSATIONS).getValuesAs(JsonObject.class);
	    for (JsonObject conversationJson : jsonConversations) {
		int conversationId = conversationJson.getInt(CONVERSATION_ID);
		JsonObject messageJson = conversationJson
			.getJsonObject(CONVERSATION_MESSAGE);

		Conversation conversation = User.get().getConversations()
			.get(conversationId);
		// New conversation
		if (conversation == null) {
		    conversation = new Conversation();
		    conversation.setId(conversationId);
		    User.get().getConversations().add(conversation);
		}
		// Existing conversation
		else {
		    Message message = new Message();
		    message.setContent(messageJson
			    .getString(CONVERSATION_MESSAGE_CONTENT));
		    message.setConversation(conversation);
		    message.setDate(JsonDateUtil.fromString(messageJson
			    .getString(CONVERSATION_MESSAGE_DATE)));
		    message.setId(messageJson.getInt(CONVERSATION_MESSAGE_ID));
		    message.setRead(true);
		    message.setSent(messageJson
			    .getBoolean(CONVERSATION_MESSAGE_SENT));
		    message.setState(State.OK);
		}
	    }
	} catch (ParseException e) {
	    throw new ActionException(e);
	}
    }

    @Override
    public boolean fromSmartphone() {
	return true;
    }

}
