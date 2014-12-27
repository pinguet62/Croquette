package fr.pinguet62.croquette.android.action.sms.exchange;

import android.os.Message;
import android.telephony.SmsMessage;
import android.util.Log;

import com.google.gson.Gson;

import fr.pinguet62.croquette.android.action.IAction;

public final class ReceivedSMSAction implements IAction {

    public static final String ACTION_VALUE = "SMS_EXCHANGE_RECEPTION";

    private static final String LOG = ReceivedSMSAction.class.getSimpleName();

    private final SmsMessage message;

    /**
     * @param message
     *            The SMS received.
     */
    public ReceivedSMSAction(SmsMessage message) {
        this.message = message;
    }

    /**
     * Create the {@link Message} from the JSON message. <br />
     * Add to {@link Conversation} and update the view.
     */
    @Override
    public void execute() {
        Log.d(LOG, "Action: " + ACTION_VALUE);

        ReceivedSmsDto dto = new ReceivedSmsDto();
        // TODO dto: SmsMessage.conversation
        dto.setConversation(null);
        dto.setPhoneNumber(message.getOriginatingAddress());
        // TODO dto: SmsMessage.id
        dto.setId(null); // TODO
        // TODO dto: SmsMessage.date
        dto.setDate(null);
        dto.setContent(message.getMessageBody());

        new Gson().toJson(dto);
    }

}
