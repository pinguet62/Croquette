package fr.pinguet62.croquette.android.xmpp;

import java.io.File;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.SmackAndroid;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.os.Build;
import android.util.Log;
import fr.pinguet62.croquette.android.MainActivity;
import fr.pinguet62.croquette.android.xmpp.smack.GTalkOAuth2SASLMechanism;
import fr.pinguet62.croquette.android.xmpp.smack.TokenCallbackHandler;

/** Singleton used to keep connection to XMPP account of user. */
public final class XMPPManager {

    private static XMPPManager INSTANCE;

    private static final String LOG = XMPPManager.class.getSimpleName();

    static {
        SASLAuthentication.registerSASLMechanism(GTalkOAuth2SASLMechanism.NAME,
                GTalkOAuth2SASLMechanism.class);
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

    /** The {@link Chat} with oneself. */
    private final Chat chat;

    private final XMPPConnection connection;

    /**
     * Create and initialize the XMPP client:
     * <ul>
     * <li>create new {@link XMPPConnection} to GTalk;</li>
     * <li>authenticate user with OAuth token;</li>
     * <li>set {@link Presence} to {@link Presence.Type#available available};</li>
     * <li>create {@link Chat} with own account.</li>
     * </ul>
     */
    private XMPPManager(String login, String token) {
        Log.i(LOG, "GTalk initialization...");

        SmackAndroid.init(MainActivity.CONTEXT);

        // Configuration
        ConnectionConfiguration config = new ConnectionConfiguration(
                "talk.google.com", 5222, "gmail.com");
        config.setSASLAuthenticationEnabled(true);
        config.setSecurityMode(SecurityMode.enabled);

        // TODO check no exception is thrown
        if (Build.VERSION.SDK_INT >= 14) {
            config.setTruststoreType("AndroidCAStore");
            config.setTruststorePassword(null);
            config.setTruststorePath(null);
        } else {
            config.setTruststoreType("BKS");
            String path = System.getProperty("javax.net.ssl.trustStore");
            if (path == null)
                path = System.getProperty("java.home") + File.separator + "etc"
                        + File.separator + "security" + File.separator
                        + "cacerts.bks";
            config.setTruststorePath(path);
        }

        // Connection
        connection = new XMPPConnection(config);
        try {
            Log.d(LOG, "Connection...");
            connection.connect();
            Log.i(LOG, "Connected");
        } catch (XMPPException exception) {
            Log.e(LOG, "Connection error", exception);
            throw new RuntimeException(exception); // TODO exception
        }

        // Authentication
        SASLAuthentication authentication = connection.getSASLAuthentication();
        TokenCallbackHandler callbackHandler = new TokenCallbackHandler(token);
        try {
            Log.d(LOG, "Authentication...");
            authentication.authenticate(login, token, callbackHandler);
            Log.i(LOG, "Authenticated");
        } catch (XMPPException exception) {
            Log.e(LOG, "Authentication error", exception);
            throw new RuntimeException(exception); // TODO exception
        }

        // Presence
        Presence presence = new Presence(Presence.Type.available);
        connection.sendPacket(presence);

        // Chat
        MessageListener listener = new MessageListenerImpl();
        // TODO login
        chat = connection.getChatManager().createChat(
                "pinguet62test@gmail.com", listener);
        chat.addMessageListener(listener);
        Log.i(LOG, "Connected to pinguet62test@gmail.com");
    }

    /** Send message to own account. */
    public void send(String message) {
        try {
            chat.sendMessage(message);
            Log.i(LOG, "XMPP message sent: " + message);
        } catch (XMPPException exception) {
            Log.e(LOG, "Error during sending XMPP message", exception);
        }
    }

}
