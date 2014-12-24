package fr.pinguet62.croquette.android.oauth;

import java.io.IOException;

import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.callback.UnsupportedCallbackException;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.util.Base64;

import android.util.Log;

/** Implementation of {@link SASLMechanism} for OAuth 2.0 authentication. */
public class GoogleOAuth2SASLMechanism extends SASLMechanism {

    private static final String LOG = GoogleOAuth2SASLMechanism.class
            .getSimpleName();

    public static final String NAME = "X-OAUTH2";

    /** The OAuth token used for authentication. */
    private String token;

    public GoogleOAuth2SASLMechanism(SASLAuthentication saslAuthentication) {
        super(saslAuthentication);
    }

    /** Send {@link Packet} to Google who contains the OAuth XML value. */
    @Override
    protected void authenticate() throws IOException, XMPPException {
        String raw = "\0" + authenticationId + "\0" + token;
        final String authenticationText = Base64.encodeBytes(
                raw.getBytes("UTF-8"), Base64.DONT_BREAK_LINES);

        // Authentication
        Packet packet = new Packet() {
            @Override
            public String toXML() {
                return "<auth mechanism=\"X-OAUTH2\""
                        + " auth:service=\"oauth2\""
                        + " xmlns:auth=\"http://www.google.com/talk/protocol/auth\""
                        + " xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">"
                        + authenticationText + "</auth>";
            }
        };
        // Send to the server
        getSASLAuthentication().send(packet);
    }

    /**
     * Used to initialize the mechanize.<br>
     * Initialize the {@link #token}.
     */
    @Override
    public void authenticate(String authenticationId, String hostname,
            CallbackHandler callbackHandler) throws IOException, XMPPException {
        this.authenticationId = authenticationId;
        this.hostname = hostname;

        // retrieve the access token with the callback handler
        TextInputCallback[] cbs = { new TextInputCallback("accessToken") };
        try {
            callbackHandler.handle(cbs);
        } catch (UnsupportedCallbackException e) {
            throw new IOException("Unsupported Callback", e);
        }
        token = cbs[0].getText();

        if (token != null)
            authenticate();
        else
            Log.i(LOG, "Token is null");
    }

    /**
     * @throws UnsupportedOperationException
     *             Not supported.
     */
    @Deprecated
    @Override
    public void authenticate(String username, String pass, String host)
            throws IOException, XMPPException {
        throw new UnsupportedOperationException(
                "Google doesn't support password authentication in OAuth2.");
    }

    /** @return {@link #NAME} */
    @Override
    protected String getName() {
        return NAME;
    }
}
