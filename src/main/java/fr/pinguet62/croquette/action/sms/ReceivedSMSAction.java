package fr.pinguet62.croquette.action.sms;

import java.util.Date;

import javax.json.JsonObject;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.model.User;

/** SMS received. */
@Action("SMS_RECEIVED")
public class ReceivedSMSAction extends SMSAction {

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

    /**
     * Create the {@link Message} from the {@link JsonObject}. <br />
     * Add to {@link Conversation} and update the view.
     */
    @Override
    public void execute() {
	// TODO Implement ReceivedSMSAction.execute();
	// WebSocket

	Message message = new Message();
	message.setDate(new Date());
	message.setContent(this.jsonMessage.getString(SMSAction.CONTENT));
	message.setContact(User.get().getContact(
		this.jsonMessage.getString(SMSAction.CONTACT_PHONE_NUMBER)));
	message.setSent(false);
    }

}
