package fr.pinguet62.croquette.android.action;

import android.util.Log;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import fr.pinguet62.croquette.android.action.sms.conversation.LoadedConversationAction;
import fr.pinguet62.croquette.android.action.sms.conversation.LoadingConversationAction;
import fr.pinguet62.croquette.android.action.sms.conversations.LoadedConversationsAction;
import fr.pinguet62.croquette.android.action.sms.conversations.LoadingConversationsAction;
import fr.pinguet62.croquette.android.action.sms.exchange.ReceivedSMSAction;
import fr.pinguet62.croquette.android.action.sms.exchange.SendSMSAction;
import fr.pinguet62.croquette.commons.action.BroadcastException;
import fr.pinguet62.croquette.commons.action.IAction;

public final class Actionfactory {

    private static final String LOG = Actionfactory.class.getSimpleName();

    /**
     * Get the implementation of {@link IAction}.
     *
     * @param json
     *            The JSON message received from webapp.
     * @return The corresponding implementation of {@link IAction}.
     * @throws IllegalArgumentException
     *             Invalid argument: bag JSON format or action key unknown.
     * @throws BroadcastException
     *             It's a broadcast message.
     * @see SmartphoneHandler
     * @see BroadcastIgnored
     */
    public static IAction getAction(String json) throws BroadcastException {
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
        String key = actionElement.getAsString();
        Log.d(LOG, "Action key: " + key);

        // Factory
        if (SendSMSAction.ACTION_VALUE.equals(key))
            return new SendSMSAction(json);
        else if (ReceivedSMSAction.ACTION_VALUE.equals(key))
            throw new BroadcastException("Action ignored: " + key);
        else if (LoadingConversationAction.ACTION_VALUE.equals(key))
            return new LoadingConversationAction(json);
        else if (LoadedConversationAction.ACTION_VALUE.equals(key))
            throw new BroadcastException("Action ignored: " + key);
        else if (LoadingConversationsAction.ACTION_VALUE.equals(key))
            return new LoadingConversationsAction(json);
        else if (LoadedConversationsAction.ACTION_VALUE.equals(key))
            throw new BroadcastException("Action ignored: " + key);
        else
            throw new IllegalArgumentException("Unknown action: " + key);
    }

    private Actionfactory() {
    }

}
