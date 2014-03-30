package fr.pinguet62.croquette.action.sms.exchange;

import javax.json.JsonObject;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.action.ActionException;
import fr.pinguet62.croquette.action.JsonDateUtil;
import fr.pinguet62.croquette.bean.SmsManagedBean;
import fr.pinguet62.croquette.model.Contact;
import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.model.Message.State;
import fr.pinguet62.croquette.model.User;

/** SMS received. */
@Action(ReceivedSMSAction.ACTION_VALUE)
public final class ReceivedSMSAction extends ExchangeSMSAction {

    /** The {@code action} value. */
    public static final String ACTION_VALUE = "SMS_RECEIVED";

    /** Key for the id of the {@link Message}. */
    public static final String ID = "id";

    /** The JSON message. */
    private final JsonObject jsonMessage;

    /**
     * Constructor.
     *
     * @param jsonMessage
     *            The JSON message.
     */
    public ReceivedSMSAction(JsonObject jsonMessage) {
	this.jsonMessage = jsonMessage;
    }

    /**
     * Create the {@link Message} from the JSON message. <br />
     * Add to {@link Conversation} and update the view.
     */
    @Override
    public void execute() {
	try {
	    Conversation conversation = null;
	    // Existing conversation
	    if (jsonMessage.containsKey(CONVERSATION)) {
		int conversationId = jsonMessage.getInt(CONVERSATION);
		conversation = User.get().getConversations()
			.get(conversationId);

		// If it's a updating of a sent message
		for (Message message : conversation)
		    if ((message.getId() == null)
			    && message.getContent().equals(
				    jsonMessage.getString(CONTENT))) {
			message.setDate(JsonDateUtil.fromString(jsonMessage
				.getString(DATE)));
			message.setId(jsonMessage.getInt(ID));
			break;
		    }
	    }
	    // New conversation
	    else {
		String phoneNumber = jsonMessage.getString(PHONE_NUMBER);
		Contact contact = User.get().getContact(phoneNumber);
		// New contact
		if (contact == null) {
		    contact = new Contact();
		    contact.setName(phoneNumber);
		    contact.setPhoneNumber(phoneNumber);
		}
		conversation = new Conversation();
		conversation.setContact(contact);
	    }

	    // Build message
	    Message message = new Message();
	    message.setContent(jsonMessage.getString(CONTENT));
	    message.setConversation(conversation);
	    message.setDate(JsonDateUtil.fromString(jsonMessage.getString(DATE)));
	    message.setId(jsonMessage.getInt(ID));
	    message.setRead(false);
	    message.setSent(false);
	    message.setState(State.OK);
	    conversation.add(message);

	    User.get().getConversations().add(conversation);
	} catch (Exception e) {
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
