package fr.pinguet62.croquette.action.sms;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

/** SMS received. */
public class ReceivedSMSAction extends SMSAction {

    /**
     * Constructor.
     * 
     * @param jsonMessage
     *            The JSON message.
     * @throws JSONException
     *             Invalid JSON message.
     */
    public ReceivedSMSAction(final JSONObject jsonMessage) throws JSONException {
	this.validate(jsonMessage);
	// TODO Implement ReceivedSMSAction.ReceivedSMSAction(JSONObject);
    }

    /** {@inheritDoc} */
    @Override
    public void execute() {
	// TODO Implement ReceivedSMSAction.execute();
	// WebSocket
    }

}
