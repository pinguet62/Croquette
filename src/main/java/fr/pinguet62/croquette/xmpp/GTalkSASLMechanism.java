package fr.pinguet62.croquette.xmpp;

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
    public GTalkSASLMechanism(final SASLAuthentication saslAuthentication) {
	super(saslAuthentication);
    }

    @Override
    protected void authenticate() throws IOException, XMPPException {
	String composedResponse = "\0" + this.authenticationId + "\0"
		+ this.password;
	byte response[] = composedResponse.getBytes("UTF-8");
	String authenticationText = Base64.encodeBytes(response,
		Base64.DONT_BREAK_LINES);

	final StringBuilder stanza = new StringBuilder();
	stanza.append("<auth ");
	stanza.append("xmlns=\"urn:ietf:params:xml:ns:xmpp-sasl\"");
	stanza.append("mechanism=\"X-OAUTH2\"");
	stanza.append("auth:service=\"oauth2\"");
	stanza.append("xmlns:auth= \"http://www.google.com/talk/protocol/auth\"");
	stanza.append(">");
	stanza.append(authenticationText);
	stanza.append("</auth>");

	Packet packet = new Packet() {
	    @Override
	    public String toXML() {
		return stanza.toString();
	    }
	};
	this.getSASLAuthentication().send(packet);
    }

    @Override
    protected String getName() {
	return GTalkSASLMechanism.NAME;
    }

}
