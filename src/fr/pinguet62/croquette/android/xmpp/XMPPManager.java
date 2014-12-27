package fr.pinguet62.croquette.android.xmpp;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.util.Log;
import fr.pinguet62.croquette.android.xmpp.smack.GTalkOAuth2SASLMechanism;
import fr.pinguet62.croquette.android.xmpp.smack.TokenCallbackHandler;

/** Singleton used to keep connection to XMPP account of user. */
public final class XMPPManager {

    private static XMPPManager INSTANCE;

    private static final String LOG = XMPPManager.class.getSimpleName();

    static {
        SASLAuthentication.registerSASLMechanism(GTalkOAuth2SASLMechanism.NAME,
                GTalkOAuth2SASLMechanism.class);
        // 0 means google sasl mechanism is the prefered one
        SASLAuthentication.supportSASLMechanism(GTalkOAuth2SASLMechanism.NAME,
                0);
    }

    public static XMPPManager getInstance() {
        return INSTANCE;
    }

    /**
     * Disconnect the existing client if it exists.<br>
     * Create new connection to the XMPP account of user.
     */
    public static void init(String login, String token) {
        if (INSTANCE != null)
            INSTANCE.connection.disconnect();
        INSTANCE = new XMPPManager(login, token);
    }

    private final XMPPConnection connection;

    /**
     * <ul>
     * <li>Create new {@link XMPPConnection} to GTalk;</li>
     * <li>Authenticate user with OAuth token;</li>
     * <li>Set {@link Presence} to {@link Presence.Type#available};</li>
     * <li>Create {@link Chat} with own account.</li>
     * </ul>
     */
    private XMPPManager(String login, String token) {
        // Connection
        ConnectionConfiguration config = new ConnectionConfiguration(
                "talk.google.com", 5222, "gmail.com");
        config.setSASLAuthenticationEnabled(true);
        config.setSecurityMode(SecurityMode.enabled);
        connection = new XMPPConnection(config);
        try {
            Log.d(LOG, "Connection...");
            connection.connect();
            Log.i(LOG, "Connected");
        } catch (XMPPException e) {
            throw new RuntimeException(e);
        }

        // Authentication
        SASLAuthentication authentication = connection.getSASLAuthentication();
        TokenCallbackHandler callbackHandler = new TokenCallbackHandler(token);
        try {
            Log.d(LOG, "Authentication...");
            authentication.authenticate(login, token, callbackHandler);
            Log.i(LOG, "Authentified");
        } catch (XMPPException e) {
            throw new RuntimeException(e);
        }

        // Presence
        Presence presence = new Presence(Presence.Type.available);
        connection.sendPacket(presence);

        // Chat
        connection.getChatManager().createChat("pinguet62@gmail.com",
                new MessageListenerImpl());
    }
}
