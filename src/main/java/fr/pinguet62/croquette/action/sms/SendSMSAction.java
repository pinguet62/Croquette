package fr.pinguet62.croquette.action.sms;

import javax.json.Json;
import javax.json.JsonObject;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.model.Message;

/** Send a SMS. */
@Action(SendSMSAction.ACTION_KEY)
public class SendSMSAction extends SMSAction {

    /** The <code>action</code> key. */
    public static final String ACTION_KEY = "SMS_SEND";

    /** The {@link Message} to send. */
    private Message message = null;

    /**
     * Constructor with {@link Message}.
     * 
     * @param message
     *            The message to send.
     */
    public SendSMSAction(final Message message) {
	this.message = message;
    }

    /** {@inheritDoc} */
    @Override
    public void execute() {
	// TODO Implement SendSMSAction.execute()
	// send JSONObject
	JsonObject json = Json
		.createObjectBuilder()
		.add(IAction.ACTION_TYPE, SendSMSAction.ACTION_KEY)
		.add(SMSAction.CONTACT_PHONE_NUMBER,
			this.message.getContact().getPhoneNumber())
		.add("content", this.message.getContent()).build();
    }

}
