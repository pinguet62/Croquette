package fr.pinguet62.croquette.model;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.pinguet62.croquette.springsecurity.OAuthAuthenticationToken;
import fr.pinguet62.croquette.xmpp.XMPPManager;

/** Informations about user. */
public final class User implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    /**
     * Gets the session user.
     *
     * @return The session user.
     */
    public static User get() {
	Authentication authentication = SecurityContextHolder.getContext()
		.getAuthentication();
	if ((authentication == null)
		|| !(authentication instanceof OAuthAuthenticationToken)
		|| !authentication.isAuthenticated())
	    return null;
	else
	    return (User) SecurityContextHolder.getContext()
		    .getAuthentication().getDetails();
    }

    /** The {@link Contact}s. */
    private final Contacts contacts = new Contacts();

    /** The {@link Conversation}s. */
    private final Conversations conversations = new Conversations();

    /** The email. */
    private String email;

    /** The OAuth token. */
    private String token;

    /** The XMPP Manager. */
    private transient XMPPManager xmppManager;

    /**
     * Gets {@link Contact} by phone number.
     *
     * @param phoneNumber
     *            The phone number
     * @return The {@link Contact}, {@code null} if not find.
     */
    public Contact getContact(String phoneNumber) {
	if (phoneNumber == null)
	    return null;
	for (Contact contact : contacts)
	    if (phoneNumber.equals(contact.getPhoneNumber()))
		return contact;
	return null;
    }

    /**
     * Gets the list of {@link Contact}s.
     *
     * @return The list of {@link Contact}s.
     */
    public Contacts getContacts() {
	return contacts;
    }

    /**
     * Gets the list of {@link Conversation}s.
     *
     * @return The list of {@link Conversation}s.
     */
    public Conversations getConversations() {
	return conversations;
    }

    /**
     * Gets the email.
     *
     * @return The email.
     */
    public String getEmail() {
	return email;
    }

    /**
     * Gets the OAuth token.
     *
     * @return The OAuth token.
     */
    public String getToken() {
	return token;
    }

    /**
     * Gets the XMPP manager.
     *
     * @return The XMPP manager.
     */
    public XMPPManager getXmppManager() {
	if ((xmppManager == null) && (token != null))
	    xmppManager = new XMPPManager();
	return xmppManager;
    }

    /**
     * Sets the email.
     *
     * @param email
     *            The email.
     */
    public void setEmail(String email) {
	this.email = email;
    }

    /**
     * Sets the OAuth token.
     *
     * @param token
     *            The OAuth token to set.
     */
    public void setToken(String token) {
	this.token = token;
    }

    /**
     * Sets the XMPP manager.
     *
     * @param xmppManager
     *            The XMPP manager.
     */
    public void setXmppManager(XMPPManager xmppManager) {
	this.xmppManager = xmppManager;
    }

}
