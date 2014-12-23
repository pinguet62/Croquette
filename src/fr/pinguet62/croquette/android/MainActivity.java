package fr.pinguet62.croquette.android;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import fr.pinguet62.croquette.android.oauth.GoogleAuthToken;

/** The main {@link Activity} */
public class MainActivity extends Activity {

    private static final int AUTH_REQUEST_CODE = 1;

    /** The {@link Log} tag. */
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == AUTH_REQUEST_CODE) && (resultCode == RESULT_OK))
            new GoogleAuthToken(this).execute();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AccountManager.get(this);

        Button connectionButton = (Button) findViewById(R.id.connectionButton);
        connectionButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View v) {
                new GoogleAuthToken(MainActivity.this).execute();
            }
        });
    }

}
