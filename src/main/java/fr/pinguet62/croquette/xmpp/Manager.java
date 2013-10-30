package fr.pinguet62.croquette.xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import fr.pinguet62.croquette.model.User;

/**
 * Manage XMPP client: <li>Connection & Disconnection; <li>Send & Receive
 * messages.
 */
public final class Manager {

    private XMPPConnection connection = null;

    /** Connect to GTalk. */
    public void connect() {
	if (this.connection != null)
	    return;

	ConnectionConfiguration configuration = new ConnectionConfiguration(
		"talk.google.com", 5222, "gmail.com");
	configuration.setSASLAuthenticationEnabled(true);

	this.connection = new XMPPConnection(configuration);
	try {
	    this.connection.connect();
	    this.connection.login(User.get().getEmail(), User.get().getToken());
	    this.connection.getRoster();
	} catch (XMPPException e) {
	    e.printStackTrace();
	}
    }

    public void disconnect() {
	if (this.connection == null)
	    return;

	this.connection.disconnect();
    }

}
