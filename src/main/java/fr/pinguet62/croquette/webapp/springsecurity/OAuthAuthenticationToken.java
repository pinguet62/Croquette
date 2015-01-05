package fr.pinguet62.croquette.webapp.springsecurity;

import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import fr.pinguet62.croquette.webapp.model.User;

/**
 * Used to authenticate the user with OAuth.
 * <p>
 * Implementation of {@link AbstractAuthenticationToken}, where
 * {@link AbstractAuthenticationToken#getDetails()} contains the {@link User}
 * object.
 * <p>
 * The default {@link GrantedAuthority} is {@code "ROLE_USER"}.
 */
public final class OAuthAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 1;

    /**
     * @param token
     *            The user's token.
     */
    public OAuthAuthenticationToken(String token) {
        super(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));

        setAuthenticated(true);

        User user = new User();
        user.setToken(token);
        setDetails(user);
    }

    /** @return {@link null} */
    @Override
    @Deprecated
    public Object getCredentials() {
        return null;
    }

    /** @return {@link null} */
    @Override
    @Deprecated
    public Object getPrincipal() {
        return null;
    }

}
