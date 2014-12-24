package fr.pinguet62.croquette.android.oauth;

import java.io.IOException;

import org.apache.harmony.javax.security.auth.callback.Callback;
import org.apache.harmony.javax.security.auth.callback.CallbackHandler;
import org.apache.harmony.javax.security.auth.callback.UnsupportedCallbackException;

public final class TokenCallbackHandler implements CallbackHandler {

    private final String token;

    public TokenCallbackHandler(String token) {
        this.token = token;
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException,
            UnsupportedCallbackException {
        for (Callback cb : callbacks)
            if (cb instanceof TextInputCallback) {
                TextInputCallback callback = (TextInputCallback) cb;
                callback.setText(token);
            } else
                throw new UnsupportedCallbackException(cb,
                        "The submitted Callback is unsupported");
    }

}
