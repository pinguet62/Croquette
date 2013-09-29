package fr.pinguet62.croquette.action;

import javax.json.JsonObject;

import fr.pinguet62.croquette.action.sms.ReceivedSMSAction;

/** Factory used to generate the corresponding {@link IAction} to execute. */
public final class ActionFactory {

    // TODO Complete ActionFactory.getAction(final JsonMessage message)
    /**
     * Get the {@link Action}.
     * 
     * @param jsonMessage
     *            The JSON message.
     * @return The {@link Action}.
     */
    public static IAction getAction(final JsonObject jsonMessage) {
	final String action = jsonMessage.getString(IAction.ACTION_TYPE);
	switch (action) {
	case ReceivedSMSAction.ACTION_KEY:
	    return new ReceivedSMSAction(jsonMessage);
	default:
	    return null;
	}
    }

}
