package fr.pinguet62.croquette.android.action.sms.exchange;

import java.util.Date;

import android.telephony.SmsMessage;
import android.util.Log;

import com.google.gson.Gson;

import fr.pinguet62.croquette.android.sms.database.Sms;
import fr.pinguet62.croquette.android.sms.database.SmsService;
import fr.pinguet62.croquette.android.xmpp.XMPPManager;
import fr.pinguet62.croquette.commons.action.IAction;
import fr.pinguet62.croquette.commons.dto.ReceivedSmsDto;

/** @see ReceivedSmsDto */
public final class ReceivedSMSAction implements IAction {

    public static final String ACTION_VALUE = "SMS_EXCHANGE_RECEPTION";

    private static final String LOG = ReceivedSMSAction.class.getSimpleName();

    private final SmsMessage message;

    /**
     * @param message
     *            The {@link SmsMessage} received.
     */
    public ReceivedSMSAction(SmsMessage message) {
        this.message = message;
    }

    @Override
    public void execute() {
        Log.d(LOG, "Action: " + ACTION_VALUE);

        // Find corresponding SMS into database
        Sms sms = SmsService.getByAddressAndBody(
                message.getOriginatingAddress(), message.getMessageBody());

        // Convert data
        ReceivedSmsDto dto = new ReceivedSmsDto();
        dto.setConversation(sms.getThreadId());
        dto.setPhoneNumber(sms.getAddress());
        dto.setId(sms.getId());
        dto.setDate(new Date(sms.getDate()));
        dto.setContent(sms.getBody());

        // Send DTO to WebApp
        String json = new Gson().toJson(dto);
        XMPPManager.getInstance().send(json);
    }
}
