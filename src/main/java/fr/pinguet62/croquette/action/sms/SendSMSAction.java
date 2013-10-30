package fr.pinguet62.croquette.action.sms;

import javax.json.Json;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.model.Message;

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
	Json.createObjectBuilder()
		.add(IAction.ACTION_KEY, SendSMSAction.ACTION_VALUE)
		.add(SMSAction.CONTENT, this.message.getContent())
		.add(SMSAction.PHONE_NUMBER,
			this.message.getConversation().getContact()
				.getPhoneNumber()).build();
    }

}
