package fr.pinguet62.croquette.webapp.model;

import java.io.Serializable;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import fr.pinguet62.croquette.webapp.springsecurity.OAuthAuthenticationToken;
import fr.pinguet62.croquette.webapp.xmpp.XMPPManager;

/** User's informations. */
public final class User implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * Get the session user.
     *
     * @return The session user.
     * @see SecurityContextHolder
     */
    public static User get() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication == null
                || !(authentication instanceof OAuthAuthenticationToken)
                || !authentication.isAuthenticated())
            return null;
        else
            return (User) SecurityContextHolder.getContext()
                    .getAuthentication().getDetails();
    }

    private final Contacts contacts = new Contacts();

    private final Conversations conversations = new Conversations();

    private String email;

    private String token;

    private transient XMPPManager xmppManager;

    public Contacts getContacts() {
        return contacts;
    }

    public Conversations getConversations() {
        return conversations;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    /**
     * Initialize {@link #xmppManager} if it {@code null} and if it can (
     * {@link #token} not {@code null}).
     */
    public XMPPManager getXmppManager() {
        if (xmppManager == null && token != null)
            xmppManager = new XMPPManager();
        return xmppManager;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setXmppManager(XMPPManager xmppManager) {
        this.xmppManager = xmppManager;
    }

}
