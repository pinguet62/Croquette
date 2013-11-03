package fr.pinguet62.croquette.action.sms;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.model.User;

/** Send a SMS. */
@Action(SendSMSAction.ACTION_VALUE)
public final class SendSMSAction extends SMSAction {

    /** The <code>action</code> value. */
    public static final String ACTION_VALUE = "SMS_SEND";

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

    /**
     * Create the JSON message from the {@link Message}. <br />
     * Send to <code>GTalk</code> account.
     */
    @Override
    public void execute() {
	JsonObjectBuilder jsonObjectBuilder = Json
		.createObjectBuilder()
		.add(IAction.ACTION_KEY, SendSMSAction.ACTION_VALUE)
		.add(SMSAction.CONTENT, this.message.getContent())
		.add(SMSAction.PHONE_NUMBER,
			this.message.getConversation().getContact()
				.getPhoneNumber());
	JsonObject jsonObject = jsonObjectBuilder.build();
	String message = jsonObject.toString();
	User.get().getXmppManager().send(message);
    }

    @Override
    public boolean fromSmartphone() {
	return false;
    }

}
