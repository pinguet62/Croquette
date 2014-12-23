package fr.pinguet62.croquette.android.oauth;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;

/** Asynchronous method who gets user token. */
public final class GoogleAuthToken extends AsyncTask<Void, Void, String> {

    /** The {@link Log} tag. */
    private static final String LOG_TAG = GoogleAuthToken.class
            .getSimpleName();

    private static final String SCOPE = "oauth2:https://www.googleapis.com/auth/googletalk";

    /** The account name. */
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
    public GoogleAuthToken(final Activity activity) {
        this.activity = activity;
        accountName = "floflo62rs@gmail.com";
        Log.i(LOG_TAG, "Account name: " + accountName);
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
            String token = GoogleAuthUtil
                    .getToken(activity, accountName, SCOPE);
            Log.i(GoogleAuthToken.LOG_TAG, "Token: " + token);
            return token;
        } catch (UserRecoverableAuthException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (IOException e) {
            Log.e(LOG_TAG, e.getMessage());
        } catch (GoogleAuthException e) {
            Log.e(LOG_TAG, e.getMessage());
        }
        return null;
    }

}
