package fr.pinguet62.croquette.android.sms;

import android.telephony.SmsManager;
import android.util.Log;

public final class SmsSender {

    private static final String LOG = SmsSender.class.getSimpleName();

    public static void send(String phoneNumber, String content) {
        Log.d(LOG, "Sending SMS to " + phoneNumber + ": " + content);
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, content, null, null);
        Log.i(LOG, "SMS sent");
    }

}
