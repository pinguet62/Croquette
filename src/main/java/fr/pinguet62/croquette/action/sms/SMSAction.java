package fr.pinguet62.croquette.action.sms;

import javax.json.JsonObject;

import fr.pinguet62.croquette.action.IAction;

/** Abstract class to define common methods and keys for JSON messages. */
public abstract class SMSAction implements IAction {

    /** Key for phone number. */
    public static final String CONTACT_PHONE_NUMBER = "phone number";

    /** Key for content. */
    public static final String CONTENT = "content";

    /**
     * Test if the JSON message is valid for SMS action.
     * 
     * @param jsonMessage
     *            The JSON message to validate.
     * @return Is valid.
     */
    protected boolean isValid(final JsonObject jsonMessage) {
	return jsonMessage.containsKey(SMSAction.CONTACT_PHONE_NUMBER);
    }

}
