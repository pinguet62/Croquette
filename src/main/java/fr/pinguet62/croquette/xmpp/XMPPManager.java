package fr.pinguet62.croquette.xmpp;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

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

    /** The XMPP chat. */
    private Chat chat = null;

    /** The XMPP connection. */
    private XMPPConnection connection = null;

    /** Connect to GTalk. */
    public void connect() {
	if (this.connection != null)
	    return;

	SASLAuthentication.registerSASLMechanism(GTalkSASLMechanism.NAME,
		GTalkSASLMechanism.class);
	SASLAuthentication.supportSASLMechanism(GTalkSASLMechanism.NAME);

	ConnectionConfiguration configuration = new ConnectionConfiguration(
		"talk.google.com", 5222, "gmail.com");
	configuration.setSASLAuthenticationEnabled(true);

	this.connection = new XMPPConnection(configuration);
	try {
	    this.connection.connect();
	    this.connection.login(User.get().getEmail(), User.get().getToken());
	    this.chat = this.connection.getChatManager().createChat(
		    User.get().getEmail(), this);
	} catch (XMPPException e) {
	}
    }

    /** Disconnect to GTalk. */
    public void disconnect() {
	if (this.connection == null)
	    return;

	this.connection.disconnect();
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
    public void processMessage(final Chat chat, final Message message) {
	String content = message.getBody();
	InputStream inputStream = new ByteArrayInputStream(content.getBytes());
	JsonReader jsonReader = Json.createReader(inputStream);
	JsonObject jsonObject = jsonReader.readObject();
	IAction action = ActionFactory.getAction(jsonObject);
	if (action.fromSmartphone())
	    action.execute();
    }

    /**
     * Send XMPP message to oneself.
     * 
     * @param message
     *            The message.
     */
    public void send(final String message) {
	try {
	    this.chat.sendMessage(message);
	} catch (XMPPException e) {
	    e.printStackTrace();
	}
    }

}
