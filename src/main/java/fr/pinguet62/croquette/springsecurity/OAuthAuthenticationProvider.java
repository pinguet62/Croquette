package fr.pinguet62.croquette.springsecurity;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

// TODO Comments class & methods
public final class OAuthAuthenticationProvider implements
	AuthenticationProvider {

    /**
     * @param authentication
     *            Contains the OAuth <code>token</code> into
     *            <code>principal</code> field.
     * @return The {@link OAuthAuthenticationToken}.
     */
    @Override
    public Authentication authenticate(final Authentication authentication)
	    throws AuthenticationException {
	String token = (String) authentication.getPrincipal();
	// Object credentials = authentication.getCredentials();
	// Collection<GrantedAuthority> authorities =
	// authentication.getAuthorities();
	return new OAuthAuthenticationToken(token);
    }

    @Override
    public boolean supports(final Class<? extends Object> authentication) {
	// TODO Auto-generated method stub
	return false;
    }

}
