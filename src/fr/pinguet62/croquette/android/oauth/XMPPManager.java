package fr.pinguet62.croquette.android.oauth;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;

import android.os.AsyncTask;
import android.util.Log;

public final class XMPPManager extends AsyncTask<Void, Void, Void> {

    private static final String LOG = XMPPManager.class.getSimpleName();

    static {
        SASLAuthentication
                .registerSASLMechanism(GoogleOAuth2SASLMechanism.NAME,
                        GoogleOAuth2SASLMechanism.class);
        // 0 means google sasl mechanism is the prefered one
        SASLAuthentication.supportSASLMechanism(GoogleOAuth2SASLMechanism.NAME,
                0);
    }

    private final String login;

    private final String token;

    public XMPPManager(String login, String token) {
        this.login = login;
        this.token = token;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Connection
        ConnectionConfiguration config = new ConnectionConfiguration(
                "talk.google.com", 5222, "gmail.com");
        config.setSASLAuthenticationEnabled(true);
        config.setSecurityMode(SecurityMode.enabled);
        XMPPConnection connection = new XMPPConnection(config);
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

        // TODO delete me
        connection.disconnect();

        return null;
    }

}
