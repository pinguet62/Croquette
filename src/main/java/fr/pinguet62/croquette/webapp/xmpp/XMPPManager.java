package fr.pinguet62.croquette.webapp.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.pinguet62.croquette.webapp.model.User;

/** Manager for XMPP client. */
public final class XMPPManager {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(XMPPManager.class);

    static {
        SASLAuthentication.registerSASLMechanism(GTalkOAuth2SASLMechanism.NAME,
                GTalkOAuth2SASLMechanism.class);
        SASLAuthentication.supportSASLMechanism(GTalkOAuth2SASLMechanism.NAME);
    }

    /** The {@link Chat} with oneself. */
    private Chat chat;

    private XMPPConnection connection = null;

    /** Connect to GTalk. */
    public void connect() {
        if (connection != null && connection.isConnected())
            return;

        LOGGER.info("GTalk initialization...");

        // Configuration
        ConnectionConfiguration configuration = new ConnectionConfiguration(
                "talk.google.com", 5222, "gmail.com");
        configuration.setSASLAuthenticationEnabled(true);

        // Connection
        connection = new XMPPConnection(configuration);
        try {
            LOGGER.debug("Connection...");
            connection.connect();
            LOGGER.info("Connected");
        } catch (XMPPException exception) {
            LOGGER.error("Connection error", exception);
            throw new RuntimeException(exception); // TODO exception
        }

        // Authentication
        try {
            LOGGER.debug("Authentication...");
            connection.login(User.get().getEmail(), User.get().getToken());
            LOGGER.info("Authenticated");
        } catch (XMPPException exception) {
            LOGGER.error("Authentication error", exception);
            throw new RuntimeException(exception); // TODO exception
        }

        // Chat
        // TODO test User.get().getEmail()
        chat = connection.getChatManager().createChat(
                "pinguet62test@gmail.com", new MessageListenerImpl());
    }

    /** Disconnect from GTalk. */
    public void disconnect() {
        if (connection == null || connection.isConnected())
            return;

        connection.disconnect();
    }

    /**
     * Send XMPP message to oneself.
     *
     * @param message
     *            The message.
     */
    public void send(String message) {
        try {
            chat.sendMessage(message);
            LOGGER.info("XMPP message sent: " + message);
        } catch (XMPPException exception) {
            LOGGER.error("Error during sending XMPP message", exception);
        }
    }

}
