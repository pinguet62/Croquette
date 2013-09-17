package fr.pinguet62.croquette.action.sms;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/** Confirm a SMS sent. */
public class ConfirmSentSMSAction extends SMSAction {

    /**
     * Constructor.
     * 
     * @param jsonMessage
     *            The JSON message.
     * @throws JSONException
     *             Invalid JSON message.
     */
    public ConfirmSentSMSAction(final JSONObject jsonMessage)
	    throws JSONException {
	this.validate(jsonMessage);
	// TODO Implement ConfirmSentSMSAction.ConfirmSentSMSAction(JSONObject);

    }

    /** {@inheritDoc} */
    @Override
    public void execute() {
	// TODO Implement ReceivedSMSAction.execute();
	// Update date
	// Save ID
	// Update sending
    }

}
