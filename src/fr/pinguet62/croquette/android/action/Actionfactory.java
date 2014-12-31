package fr.pinguet62.croquette.android.action;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import fr.pinguet62.croquette.android.action.sms.conversation.LoadedConversationAction;
import fr.pinguet62.croquette.android.action.sms.conversation.LoadingConversationAction;
import fr.pinguet62.croquette.android.action.sms.exchange.ReceivedSMSAction;
import fr.pinguet62.croquette.android.action.sms.exchange.SendSMSAction;

public final class Actionfactory {

    /**
     * @param json
     *            The JSON message.
     * @return The implementation of {@link IAction}.<br>
     *         If it's an {@link IAction action} send previously and received
     *         because of broadcast, return {@code null}.
     * @throws IllegalArgumentException
     *             Bad JSON message: invalid format or missing {@code action}
     *             key.
     * @throws UnsupportedOperationException
     *             Unknown action.
     */
    public static IAction getAction(String json) {
        // Get action key
        JsonElement root;
        try {
            root = new JsonParser().parse(json);
        } catch (JsonSyntaxException e) {
            throw new IllegalArgumentException("Bad JSON format", e);
        }
        JsonElement actionElement = root.getAsJsonObject().get("action");
        if (actionElement == null)
            throw new IllegalArgumentException("Missing \"action\" key");
        String action = actionElement.getAsString();

        // Factory
        if (SendSMSAction.ACTION_VALUE.equals(action))
            return new SendSMSAction(json);
        else if (ReceivedSMSAction.ACTION_VALUE.equals(action))
            return null;
        else if (LoadingConversationAction.ACTION_VALUE.equals(action))
            return new LoadingConversationAction(json);
        else if (LoadedConversationAction.ACTION_VALUE.equals(action))
            return null;
        else
            throw new UnsupportedOperationException("Unknown action");
    }

    /** Private constructor. */
    private Actionfactory() {
    }

}
