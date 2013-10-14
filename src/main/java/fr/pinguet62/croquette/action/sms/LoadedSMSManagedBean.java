package fr.pinguet62.croquette.action.sms;

import java.text.DateFormat;
import java.util.List;

import javax.json.JsonObject;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.action.ActionException;
import fr.pinguet62.croquette.model.Contact;
import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.model.Message.State;
import fr.pinguet62.croquette.model.User;

/** Old SMS of a {@link Conversation} received. */
@Action(LoadedSMSManagedBean.ACTION_VALUE)
public final class LoadedSMSManagedBean extends SMSAction {

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
    public LoadedSMSManagedBean(final JsonObject jsonMessage) {
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

	    List<JsonObject> jsonMessages = this.jsonMessage.getJsonArray(
		    LoadedSMSManagedBean.MESSAGES)
		    .getValuesAs(JsonObject.class);
	    for (JsonObject jsonMessage : jsonMessages) {
		Message message = new Message();
		message.setContact(contact);
		message.setContent(jsonMessage.getString(SMSAction.CONTENT));
		message.setDate(DateFormat.getDateTimeInstance(
			DateFormat.DEFAULT, DateFormat.DEFAULT).parse(
			this.jsonMessage.getString(SMSAction.DATE)));
		message.setRead(true);
		message.setSent(jsonMessage
			.getBoolean(LoadedSMSManagedBean.SENT));
		message.setState(State.OK);
	    }
	} catch (Exception e) {
	    throw new ActionException(e);
	}
    }

}
