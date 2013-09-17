package fr.pinguet62.croquette.action.sms;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/**
 * Send a SMS.
 * 
 * @author Pinguet62
 */
public class SendSMSAction extends SMSAction {

    /**
     * Constructor.
     * 
     * @param jsonMessage
     *            The JSON message.
     * @throws JSONException
     *             Invalid tag.
     */
    public SendSMSAction(final JSONObject jsonMessage) throws JSONException {
	jsonMessage.getString(SMSAction.CONTACT_PHONE_NUMBER);
    }

    /** {@inheritDoc} */
    @Override
    public void execute() {
	// TODO Implement SendSMSAction.execute()
    }

}
