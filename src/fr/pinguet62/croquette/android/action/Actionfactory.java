package fr.pinguet62.croquette.android.action;

import com.google.gson.JsonParser;

import fr.pinguet62.croquette.android.action.sms.conversation.LoadingConversationAction;
import fr.pinguet62.croquette.android.action.sms.exchange.SendSMSAction;

public final class Actionfactory {

    /**
     * @param json
     *            The JSON message.
     * @return The implementation of {@link IAction}.
     * @throws UnsupportedOperationException
     *             Unknown action.
     */
    public static IAction getAction(String json) {
        String action = new JsonParser().parse(json).getAsJsonObject()
                .get("action").getAsString();
        if (SendSMSAction.ACTION_VALUE.equals(action))
            return new SendSMSAction(json);
        else if (LoadingConversationAction.ACTION_VALUE.equals(action))
            return new LoadingConversationAction(json);
        else
            throw new UnsupportedOperationException("Unknown action");
    }

    /** Private constructor. */
    private Actionfactory() {
    }

}
