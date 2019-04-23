package fr.pinguet62.croquette.android.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

import android.util.Log;
import fr.pinguet62.croquette.android.action.Actionfactory;
import fr.pinguet62.croquette.commons.action.BroadcastException;
import fr.pinguet62.croquette.commons.action.IAction;

/**
 * Handler for XMPP messages.
 * <p>
 * Use the {@link ActionFactory} to get the corresponding {@link IAction}, and
 * execute it.
 *
 * @see IAction
 * @see Actionfactory
 */
public final class MessageListenerImpl implements MessageListener {

    private static final String LOG = MessageListenerImpl.class.getSimpleName();

    @Override
    public void processMessage(Chat chat, Message message) {
        String content = message.getBody();
        Log.i(LOG, "XMPP message received: " + content);

        if (content == null) {
            Log.i(LOG, "Unknown null message: ignored");
            return;
        }

        // Factory
        IAction action;
        try {
            action = Actionfactory.getAction(content);
        } catch (IllegalArgumentException exception) {
            Log.i(LOG, "Invalid XMPP message", exception);
            return;
        } catch (BroadcastException exception) {
            Log.d(LOG, "Brodcast message: ignored");
            return;
        }

        // Execute
        action.execute();
    }
}
