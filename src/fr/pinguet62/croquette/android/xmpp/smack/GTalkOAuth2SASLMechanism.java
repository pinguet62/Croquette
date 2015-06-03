package fr.pinguet62.croquette.android.xmpp.smack;

import java.io.IOException;

import org.apache.harmony.javax.security.auth.callback.Callback;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.callback.UnsupportedCallbackException;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.util.Base64;

import android.util.Log;

/**
 * Implementation of {@link SASLMechanism} for OAuth 2.0 authentication on
 * GTalk.
 *
 * @see TokenCallback
 * @see TokenCallbackHandler
 */
public class GTalkOAuth2SASLMechanism extends SASLMechanism {

    private static final String LOG = GTalkOAuth2SASLMechanism.class
            .getSimpleName();

    /** The name of this SASL mechanism. */
    public static final String NAME = "X-OAUTH2";

    /** The OAuth token used for authentication. */
    private String token;

    public GTalkOAuth2SASLMechanism(SASLAuthentication saslAuthentication) {
        super(saslAuthentication);
    }

    /** Send {@link Packet} to Google who contains the OAuth XML value. */
    @Override
    protected void authenticate() throws IOException, XMPPException {
        String composedResponse = "\0" + authenticationId + "\0" + token;
        byte[] response = composedResponse.getBytes("UTF-8");
        String authenticationText = Base64.encodeBytes(response,
                Base64.DONT_BREAK_LINES);

        final String stanza = "<auth mechanism=\"" + NAME + "\""
                + " auth:service=\"oauth2\""
                + " xmlns:auth=\"http://www.google.com/talk/protocol/auth\""
                + " xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\">"
                + authenticationText + "</auth>";
        Log.d(LOG, stanza);

        // Authentication
        Packet packet = new Packet() {
            @Override
            public String toXML() {
                return stanza;
            }
        };
        // Send to the server
        getSASLAuthentication().send(packet);
    }

    /**
     * Used to initialize the mechanize.<br>
     * Initialize the {@link #token}.
     * <p>
     * Call {@link #authenticate()} after getting token.
     */
    @Override
    public void authenticate(String authenticationId, String hostname,
            CallbackHandler callbackHandler) throws IOException, XMPPException {
        // not used
        this.authenticationId = authenticationId;
        this.hostname = hostname;

        // Retrieve the token with the callback handler
        TokenCallback tokenCallback = new TokenCallback();
        try {
            callbackHandler.handle(new Callback[] { tokenCallback });
        } catch (UnsupportedCallbackException e) {
            throw new IOException("Unsupported Callback", e);
        }
        token = tokenCallback.getToken();

        if (token != null)
            authenticate();
        else
            throw new RuntimeException("Token is null");
    }

    /**
     * @throws UnsupportedOperationException
     *             Not supported.
     */
    @Override
    @Deprecated
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
