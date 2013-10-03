package fr.pinguet62.croquette.oauth;

import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import fr.pinguet62.croquette.model.User;

/**
 * Used to authenticate the user with OAuth. <br />
 * Simple extension of {@link AbstractAuthenticationToken} because
 * <code>username</code> and <code>password</code> are not used. <br />
 * Define the default <code>ROLE_USER</code> right.
 */
public final class OAuthAuthenticationToken extends AbstractAuthenticationToken {

    /** Auto-generated serial version UID. */
    private static final long serialVersionUID = -7960714936545188027L;

    /**
     * Constructor.
     * 
     * @param token
     *            The user token.
     */
    public OAuthAuthenticationToken(final String token) {
	super(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));
	super.setDetails(new User());
	super.setAuthenticated(true);
    }

    /**
     * No used.
     * 
     * @return <code>null</code>
     */
    @Override
    public Object getCredentials() {
	return null;
    }

    /**
     * No used.
     * 
     * @return <code>null</code>
     */
    @Override
    public Object getPrincipal() {
	return null;
    }

}
