package fr.pinguet62.croquette.webapp.xmpp;

import java.io.IOException;

import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.sasl.SASLMechanism;
import org.jivesoftware.smack.util.Base64;

/** SASL mechanism for GTalk. */
public final class GTalkSASLMechanism extends SASLMechanism {

    /** The name of this SASL mechanism. */
    public static final String NAME = "X-GOOGLE-TOKEN";

    /**
     * Constructor.
     *
     * @param saslAuthentication
     *            The authentication.
     */
    public GTalkSASLMechanism(SASLAuthentication saslAuthentication) {
        super(saslAuthentication);
    }

    @Override
    protected void authenticate() throws IOException, XMPPException {
        String composedResponse = "\0" + authenticationId + "\0" + password;
        byte response[] = composedResponse.getBytes("UTF-8");
        String authenticationText = Base64.encodeBytes(response,
                Base64.DONT_BREAK_LINES);

        // TODO Java 8: remove final keyword
        String stanza = new StringBuilder("<auth ")
        .append("xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\"")
        .append("mechanism=\"X-OAUTH2\"")
        .append("auth:service=\"oauth2\"")
        .append("xmlns:auth= \"http://www.google.com/talk/protocol/auth\"")
        .append(">").append(authenticationText).append("</auth>")
        .toString();

        Packet packet = new Packet() {
            @Override
            public String toXML() {
                return stanza;
            }
        };
        getSASLAuthentication().send(packet);
    }

    @Override
    protected String getName() {
        return GTalkSASLMechanism.NAME;
    }

}
