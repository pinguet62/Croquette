package fr.pinguet62.croquette.action.sms;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import fr.pinguet62.croquette.action.IAction;

/** Abstract class to define common methods and keys for JSON messages. */
public abstract class SMSAction implements IAction {

    /** Key for phone number. */
    public static final String CONTACT_PHONE_NUMBER = "phone number";

    /**
     * Validate the JSON message for SMS action.
     * 
     * @param jsonMessage
     *            The JSON message to validate.
     * @throws JSONException
     *             Invalid JSON message.
     */
    protected void validate(final JSONObject jsonMessage) throws JSONException {
	jsonMessage.get(SMSAction.CONTACT_PHONE_NUMBER);
    }

}
