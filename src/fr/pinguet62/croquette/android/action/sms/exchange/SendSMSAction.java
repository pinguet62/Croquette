package fr.pinguet62.croquette.android.action.sms.exchange;

import android.util.Log;

import com.google.gson.Gson;

import fr.pinguet62.croquette.android.action.IAction;
import fr.pinguet62.croquette.android.sms.SmsSender;

/**
 * @see SendSmsDto
 * @see SmsSender
 */
public final class SendSMSAction implements IAction {

    public static final String ACTION_VALUE = "SMS_EXCHANGE_SENDIND";

    private static final String LOG = SendSMSAction.class.getSimpleName();

    private final String json;

    public SendSMSAction(String json) {
        this.json = json;
    }

    @Override
    public void execute() {
        Log.d(LOG, "Action: " + ACTION_VALUE);

        SendSmsDto dto = new Gson().fromJson(json.toString(), SendSmsDto.class);

        // Send SMS
        SmsSender.send(dto.getPhoneNumber(), dto.getContent());
    }

}
