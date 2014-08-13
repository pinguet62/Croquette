package fr.pinguet62.croquette.xmpp;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonParsingException;

import org.apache.log4j.Logger;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import fr.pinguet62.croquette.action.ActionFactory;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.model.User;

/**
 * Manage XMPP client: <li>Connection & Disconnection; <li>Send & Receive
 * messages.
 */
public final class XMPPManager implements MessageListener {

    /** The logger. */
    private static final Logger LOGGER = Logger.getLogger(XMPPManager.class);

    /** The XMPP chat. */
    private Chat chat = null;

    /** The XMPP connection. */
    private XMPPConnection connection = null;

    /** Connect to GTalk. */
    public void connect() {
        if (connection != null)
            return;

        SASLAuthentication.registerSASLMechanism(GTalkSASLMechanism.NAME,
                GTalkSASLMechanism.class);
        SASLAuthentication.supportSASLMechanism(GTalkSASLMechanism.NAME);

        ConnectionConfiguration configuration = new ConnectionConfiguration(
                "talk.google.com", 5222, "gmail.com");
        configuration.setSASLAuthenticationEnabled(true);

        connection = new XMPPConnection(configuration);
        try {
            connection.connect();
            connection.login(User.get().getEmail(), User.get().getToken());
            chat = connection.getChatManager().createChat(
                    User.get().getEmail(), this);
        } catch (XMPPException exception) {
            LOGGER.error("Error during XMPP connection.", exception);
        }
    }

    /** Disconnect to GTalk. */
    public void disconnect() {
        if (connection == null)
            return;

        connection.disconnect();
    }

    /**
     * Handler when user receive {@link Message}.
     *
     * @param chat
     *            The {@link Chat}.
     * @param messahe
     *            the {@link Message}.
     */
    @Override
    public void processMessage(Chat chat, Message message) {
        // TODO Test: sender = current user

        String content = message.getBody();
        LOGGER.info("XMPP message received: " + content);

        try (JsonReader jsonReader = Json
                .createReader(new StringReader(content))) {
            JsonObject jsonObject = jsonReader.readObject();
            IAction action = ActionFactory.getAction(jsonObject);
            if (action == null) {
                LOGGER.error("Action not found.");
                return;
            }
            if (action.fromSmartphone())
                action.execute();
        } catch (JsonParsingException exception) {
            LOGGER.error("Invalid JSON message.");
        }
    }

    /**
     * Send XMPP message to oneself.
     *
     * @param message
     *            The message.
     */
    public void send(String message) {
        LOGGER.info("XMPP message sending: " + message);
        try {
            chat.sendMessage(message);
        } catch (XMPPException e) {
            LOGGER.error("Error during XMPP sending.");
        }
    }

}
