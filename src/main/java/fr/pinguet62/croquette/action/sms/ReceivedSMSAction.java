package fr.pinguet62.croquette.action.sms;

import javax.json.JsonObject;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.model.User;

/** SMS received. */
@Action(ReceivedSMSAction.ACTION_KEY)
public class ReceivedSMSAction extends SMSAction {

    /** The <code>action</code> key. */
    public static final String ACTION_KEY = "SMS_RECEIVED";

    /** The JSON message. */
    private JsonObject jsonMessage = null;

    /**
     * Constructor.
     * 
     * @param jsonMessage
     *            The JSON message.
     */
    public ReceivedSMSAction(final JsonObject jsonMessage) {
	this.jsonMessage = jsonMessage;
    }

    /** {@inheritDoc} */
    @Override
    public void execute() {
	// TODO Implement ReceivedSMSAction.execute();
	// WebSocket

	Message message = new Message();
	message.setContent(this.jsonMessage.getString(SMSAction.CONTENT));
	User.get().getContact(
		this.jsonMessage.getString(SMSAction.CONTACT_PHONE_NUMBER));
    }

}
