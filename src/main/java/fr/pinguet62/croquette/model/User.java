package fr.pinguet62.croquette.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.pinguet62.croquette.springsecurity.OAuthAuthenticationToken;
import fr.pinguet62.croquette.xmpp.Manager;

/** Informations about user. */
public final class User {

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
    private String email = null;

    /** The OAuth token. */
    private String token = null;

    /** The XMPP Manager. */
    private Manager xmppManager = null;

    /**
     * Gets {@link Contact} by phone number.
     * 
     * @param phoneNumber
     *            The phone number
     * @return The {@link Contact}, <code>null</code> if not find.
     */
    public Contact getContact(final String phoneNumber) {
	if (phoneNumber == null)
	    return null;
	for (Contact contact : this.contacts)
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
	return this.contacts;
    }

    /**
     * Gets the list of {@link Conversation}s.
     * 
     * @return The list of {@link Conversation}s.
     */
    public Conversations getConversations() {
	return this.conversations;
    }

    /**
     * Gets the email.
     * 
     * @return The email.
     */
    public String getEmail() {
	return this.email;
    }

    /**
     * Gets the OAuth token.
     * 
     * @return The OAuth token.
     */
    public String getToken() {
	return this.token;
    }

    /**
     * Gets the XMPP manager.
     * 
     * @return The XMPP manager.
     */
    public Manager getXmppManager() {
	return this.xmppManager;
    }

    /**
     * Sets the email.
     * 
     * @param email
     *            The email.
     */
    public void setEmail(final String email) {
	this.email = email;
    }

    /**
     * Sets the OAuth token.
     * 
     * @param token
     *            The OAuth token to set.
     */
    public void setToken(final String token) {
	this.token = token;
    }

    /**
     * Sets the XMPP manager.
     * 
     * @param xmppManager
     *            The XMPP manager.
     */
    public void setXmppManager(final Manager xmppManager) {
	this.xmppManager = xmppManager;
    }

}
