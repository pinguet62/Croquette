package fr.pinguet62.croquette.springsecurity;

import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import fr.pinguet62.croquette.model.User;

/**
 * Used to authenticate the user with OAuth. <br />
 * Simple extension of {@link AbstractAuthenticationToken}, where
 * {@link AbstractAuthenticationToken#getDetails()} contains the {@link User}
 * object, and {@link GrantedAuthority} is {@code ROLE_USER}.
 */
public final class OAuthAuthenticationToken extends AbstractAuthenticationToken {

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    /**
     * Constructor.
     *
     * @param token
     *            The user token.
     */
    public OAuthAuthenticationToken(String token) {
	super(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

	setAuthenticated(true);

	User user = new User();
	user.setToken(token);
	setDetails(user);
    }

    @Override
    public Object getCredentials() {
	return null;
    }

    @Override
    public Object getPrincipal() {
	return null;
    }

}
