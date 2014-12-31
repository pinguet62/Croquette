package fr.pinguet62.croquette.android.oauth;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

import fr.pinguet62.croquette.android.MainActivity;
import fr.pinguet62.croquette.android.xmpp.XMPPManager;

/** Asynchronous function used to get user's OAuth token. */
public final class GoogleAuthToken extends AsyncTask<Void, Void, String> {

    /** The {@link Log} tag. */
    private static final String LOG = GoogleAuthToken.class.getSimpleName();

    private final String accountName;

    /** The {@link Context}. */
    private final Activity activity;

    /**
     * Constructor.
     *
     * @param activity
     *            The {@link Context}.
     * @param accountName
     *            The account name.
     */
    public GoogleAuthToken(Activity activity, String accountName) {
        this.activity = activity;
        this.accountName = accountName;
    }

    /**
     * Method launched to get the OAuth token.
     *
     * @param args
     *            Not used.
     */
    @Override
    protected String doInBackground(final Void... args) {
        try {
            // Token
            String token = GoogleAuthUtil.getToken(activity, accountName,
                    "oauth2:https://www.googleapis.com/auth/googletalk");
            Log.i(GoogleAuthToken.LOG, "Token: " + token);
            return token;
        } catch (UserRecoverableAuthException e) {
            Log.e(LOG, e.getMessage());
            // Request permission
            Log.i(LOG, "Request permission...");
            activity.startActivityForResult(e.getIntent(),
                    MainActivity.REQUEST_OAUTH_AUTHORIZATION);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (GoogleAuthException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    /**
     * Connect user to XMPP server.
     *
     * @param token
     *            The OAuth token returned by {@link #doInBackground(Void...)}.
     */
    @Override
    protected void onPostExecute(String token) {
        super.onPostExecute(token);

        XMPPManager.init(accountName, token);
    }

}
