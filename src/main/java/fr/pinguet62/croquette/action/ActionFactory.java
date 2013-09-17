package fr.pinguet62.croquette.action;

import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;

import fr.pinguet62.croquette.action.sms.ConfirmSentSMSAction;
import fr.pinguet62.croquette.action.sms.ReceivedSMSAction;
import fr.pinguet62.croquette.action.sms.SendSMSAction;

/**
 * Factory used to generate the corresponding {@link IAction} to execute.
 * 
 * @author Pinguet62
 */
public final class ActionFactory {

    // TODO Complete ActionFactory.getAction(final JsonMessage message)
    /**
     * Get the {@link Action}.
     * 
     * @param jsonMessage
     *            The JSON message.
     * @return The {@link Action}.
     * @author Pinguet62
     */
    public static IAction getAction(final JSONObject jsonMessage) {
	try {
	    final String strAction = jsonMessage.getString(IAction.ACTION);
	    final ActionType type = ActionType.fromValue(strAction);

	    switch (type) {
	    case SMS_RECEIVED:
		return new ReceivedSMSAction(jsonMessage);
	    case SMS_SENT:
		return new SendSMSAction(jsonMessage);
	    case SMS_SENT_CONFIRM:
		return new ConfirmSentSMSAction(jsonMessage);
	    }
	} catch (final JSONException e) {
	}
	return null;
    }

}
