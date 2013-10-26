package fr.pinguet62.croquette.springsecurity;

import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import fr.pinguet62.croquette.model.User;

/**
 * Used to authenticate the user with OAuth. <br />
 * Simple extension of {@link UsernamePasswordAuthenticationToken}, where: <li>
 * <code>principal</code> is the <code>token</code>; <li>
 * <code>credentials</code> is null; <li> <code>detail</code> is the {@link User}
 * object; <li> {@link GrantedAuthority} is <code>ROLE_USER</code>.
 */
public final class OAuthAuthenticationToken extends
	UsernamePasswordAuthenticationToken {

    /** Auto-generated serial version UID. */
    private static final long serialVersionUID = -7960714936545188027L;

    /**
     * Constructor.
     * 
     * @param token
     *            The user token.
     */
    public OAuthAuthenticationToken(final String token) {
	super(token, null, Collections.singleton(new SimpleGrantedAuthority(
		"ROLE_USER")));

	User user = new User();
	user.setToken(token);
	this.setDetails(user);
    }

}
