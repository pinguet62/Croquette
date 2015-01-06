package fr.pinguet62.croquette.webapp.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pinguet62.croquette.webapp.action.ActionFactory;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.action.SmartphoneHandler;

/**
 * Handler for XMPP messages.
 * <p>
 * Use the {@link ActionFactory} to get the corresponding {@link IAction}, and
 * execute it.
 *
 * @see IAction
 * @see ActionFactory
 * @see SmartphoneHandler
 */
public final class MessageListenerImpl implements MessageListener {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MessageListenerImpl.class);

    @Override
    public void processMessage(Chat chat, Message message) {
        String content = message.getBody();
        LOGGER.info("XMPP message received: " + content);

        // Factory
        IAction action;
        try {
            action = ActionFactory.getAction(content);
        } catch (IllegalArgumentException exception) {
            LOGGER.info("Invalid XMPP message", exception);
            return;
        }

        // Broadcast
        if (action == null) {
            LOGGER.debug("Brodcast message: ignored");
            return;
        }

        // Execute
        action.execute();
    }

}
