package fr.pinguet62.croquette.android;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.AccountPicker;

import fr.pinguet62.croquette.android.oauth.GoogleAuthToken;

/** The main {@link Activity} */
public class MainActivity extends Activity {

    private static final int AUTH_REQUEST_CODE = 1;

    // TODO evol
    public static Context CONTEXT;

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static final int USER_RECOVERABLE_AUTH = 2;

    // TODO evol
    public MainActivity() {
        CONTEXT = this;
    }

    /** Get the user account and run the function to get OAuth token. */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String login = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        Log.i(LOG_TAG, "Account name: " + login);

        if ((requestCode == AUTH_REQUEST_CODE) && (resultCode == RESULT_OK))
            requestToken(login);
        else if (resultCode == USER_RECOVERABLE_AUTH)
            requestToken(login);
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void requestToken(String login) {
        new GoogleAuthToken(this, login).execute();
    }

    /**
     * Used to initialize the {@link Intent} by finding the existing accounts.
     * <p>
     * The method {@link #onActivityResult(int, int, Intent)} is called
     * automatically after this method.
     */
    private void selectAccountToUse() {
        Intent intent = AccountPicker.newChooseAccountIntent(null, null,
                new String[] { "com.google" }, false, null, null, null, null);
        startActivityForResult(intent, AUTH_REQUEST_CODE);
    }

    // TODO test
    public void test() {
        selectAccountToUse();
    }

}
