package fr.pinguet62.croquette.android.sms;

import android.telephony.SmsMessage;
import android.util.Log;

public final class SmsSender {

    private static final String LOG = SmsSender.class.getSimpleName();

    public static void send(SmsMessage smsMessage) {
        Log.d(LOG, "Sending SMS...");
        // SmsManager smsManager = SmsManager.getDefault();
        // smsManager.sendTextMessage("phone number", null, msg.getRecipient(),
        // null, null);
        Log.i(LOG, "SMS sent");
    }

}
