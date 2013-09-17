package fr.pinguet62.croquette.action.sms;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import fr.pinguet62.croquette.action.ActionType;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.model.Message;

/** Send a SMS. */
public class SendSMSAction extends SMSAction {

    /** The JSON message. */
    private JSONObject jsonMessage = null;

    /**
     * Constructor with JSON message.
     * 
     * @param jsonMessage
     *            The JSON message.
     * @throws JSONException
     *             Invalid JSON message.
     */
    public SendSMSAction(final JSONObject jsonMessage) throws JSONException {
	this.validate(jsonMessage);
	this.jsonMessage = jsonMessage;
    }

    /**
     * Constructor with {@link Message}.
     * 
     * @param message
     *            The message to send.
     * @throws JSONException
     *             Invalid JSON message.
     */
    public SendSMSAction(final Message message) throws JSONException {
	// Implement SendSMSAction.SendSMSAction(Message)
	this.jsonMessage = new JSONObject();
	this.jsonMessage.append(IAction.ACTION_TYPE, ActionType.SMS_SEND);
	this.jsonMessage.append(SMSAction.CONTACT_PHONE_NUMBER, message
		.getContact().getPhoneNumber());
    }

    /** {@inheritDoc} */
    @Override
    public void execute() {
	// TODO Implement SendSMSAction.execute()
	// send JSONObject
	this.jsonMessage = new JSONObject(); // tmp
    }

}
