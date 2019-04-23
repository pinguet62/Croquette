package fr.pinguet62.croquette.android.xmpp.smack;

import org.apache.harmony.javax.security.auth.callback.Callback;

/**
 * Simple {@link Callback} as POJO used to store the token.
 *
 * @see TokenCallbackHandler
 */
public final class TokenCallback implements Callback {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
