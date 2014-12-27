package fr.pinguet62.croquette.android.sms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import fr.pinguet62.croquette.android.action.IAction;
import fr.pinguet62.croquette.android.action.sms.exchange.ReceivedSMSAction;

/**
 * Handler for SMS receptions.
 * <p>
 * Send the content and informations of {@link SmsMessage} to Smartphone by
 * calling {@link ReceivedSMSAction}.
 *
 * @see ReceivedSMSAction
 */
public final class SmsReceiver extends BroadcastReceiver {

    private static final String LOG = SmsReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(LOG, "SMS received");

        Bundle bundle = intent.getExtras();
        byte[][] pdus = (byte[][]) bundle.get("pdus");
        // Convert
        SmsMessage[] smsMessages = new SmsMessage[pdus.length];
        for (int i = 0; i < pdus.length; i++)
            smsMessages[i] = SmsMessage.createFromPdu(pdus[i]);

        // Execute
        IAction action = new ReceivedSMSAction(smsMessages[0]);
        action.execute();
    }

}
