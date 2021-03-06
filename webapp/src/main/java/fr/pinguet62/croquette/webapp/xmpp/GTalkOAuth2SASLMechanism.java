package fr.pinguet62.croquette.webapp.xmpp;

import java.io.IOException;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link SASLMechanism} for OAuth 2.0 authentication on
 * GTalk.
 */
public final class GTalkOAuth2SASLMechanism extends SASLMechanism {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(GTalkOAuth2SASLMechanism.class);

    /** The name of this SASL mechanism. */
    public static final String NAME = "X-OAUTH2";

    public GTalkOAuth2SASLMechanism(SASLAuthentication saslAuthentication) {
        super(saslAuthentication);
    }

    @Override
    protected void authenticate() throws IOException, XMPPException {
        String composedResponse = "\0" + authenticationId + "\0" + password;
        byte[] response = composedResponse.getBytes("UTF-8");
        String authenticationText = Base64.encodeBytes(response,
                Base64.DONT_BREAK_LINES);

        String stanza = new StringBuilder("<auth ")
                .append("xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\" ")
                .append("mechanism=\"" + NAME + "\" ")
                .append("auth:service=\"oauth2\" ")
                .append("xmlns:auth=\"http://www.google.com/talk/protocol/auth\"")
                .append(">").append(authenticationText).append("</auth>")
                .toString();
        LOGGER.debug(stanza);

        Packet packet = new Packet() {
            @Override
            public String toXML() {
                return stanza;
            }
        };
        getSASLAuthentication().send(packet);
    }

    /** @return {@link #NAME} */
    @Override
    protected String getName() {
        return GTalkOAuth2SASLMechanism.NAME;
    }

}
