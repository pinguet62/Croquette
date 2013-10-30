package fr.pinguet62.croquette.action.sms;

import java.text.DateFormat;
import java.util.List;

import javax.json.JsonObject;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.action.ActionException;
import fr.pinguet62.croquette.bean.SmsManagedBean;
import fr.pinguet62.croquette.model.Contact;
import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.model.Message.State;
import fr.pinguet62.croquette.model.User;

/** Old SMS of a {@link Conversation} received. */
@Action(LoadedSMSAction.ACTION_VALUE)
public final class LoadedSMSAction extends SMSAction {

    /** The <code>action</code> value. */
    public static final String ACTION_VALUE = "SMS_LOADED";

    /** Key for number of SMS to load. */
    public static final String MESSAGES = "messages";

    /** Key for boolean to define if the SMS was sent or not. */
    public static final String SENT = "messages";

    /** The JSON message. */
    private JsonObject jsonMessage = null;

    /**
     * Constructor.
     * 
     * @param jsonMessage
     *            The JSON message.
     */
    public LoadedSMSAction(final JsonObject jsonMessage) {
	this.jsonMessage = jsonMessage;
    }

    /**
     * Extract the {@link Message}s from the JSON message.<br />
     * Add to {@link Conversation}.
     */
    @Override
    public void execute() {
	try {
	    Contact contact = User.get().getContact(
		    this.jsonMessage.getString(SMSAction.PHONE_NUMBER));

	    // Get each message
	    List<JsonObject> jsonMessages = this.jsonMessage.getJsonArray(
		    LoadedSMSAction.MESSAGES).getValuesAs(JsonObject.class);
	    for (JsonObject jsonMessage : jsonMessages) {
		Message message = new Message();
		message.setContent(jsonMessage.getString(SMSAction.CONTENT));
		message.setConversation(null); // TODO
		message.setDate(DateFormat.getDateTimeInstance(
			DateFormat.DEFAULT, DateFormat.DEFAULT).parse(
			jsonMessage.getString(SMSAction.DATE)));
		message.setRead(true);
		message.setSent(jsonMessage.getBoolean(LoadedSMSAction.SENT));
		message.setState(State.OK);

		contact.getConversation().add(message);
	    }

	    // If the number of message is not equals to max count, then
	    // conversation will not have other messages
	    if (jsonMessages.size() != LoadindSMSAction.COUNT_VALUE)
		contact.getConversation().setHasOldMessages(false);
	} catch (Exception e) {
	    throw new ActionException(e);
	}

	PushContext pushContext = PushContextFactory.getDefault()
		.getPushContext();
	pushContext.push(SmsManagedBean.CHANNEL, null);
    }

}
