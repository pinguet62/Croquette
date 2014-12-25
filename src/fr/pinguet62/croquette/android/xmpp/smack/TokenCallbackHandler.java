package fr.pinguet62.croquette.android.xmpp.smack;

import java.io.IOException;

import org.apache.harmony.javax.security.auth.callback.Callback;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.callback.UnsupportedCallbackException;

/**
 * {@link CallbackHandler} used to store the token into a {@link Callback}.
 *
 * @see TokenCallback
 */
public final class TokenCallbackHandler implements CallbackHandler {

    private final String token;

    public TokenCallbackHandler(String token) {
        this.token = token;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException,
    UnsupportedCallbackException {
        for (Callback callback : callbacks)
            if (callback instanceof TokenCallback) {
                TokenCallback tokenCallback = (TokenCallback) callback;
                tokenCallback.setToken(token);
            } else
                throw new UnsupportedCallbackException(callback,
                        "The submitted Callback is unsupported");
    }

}
