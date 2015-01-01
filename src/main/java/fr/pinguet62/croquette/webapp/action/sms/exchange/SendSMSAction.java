package fr.pinguet62.croquette.webapp.action.sms.exchange;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import fr.pinguet62.croquette.webapp.action.Action;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.User;

/** Send a SMS. */
@Action(SendSMSAction.ACTION_VALUE)
public final class SendSMSAction extends ExchangeSMSAction {

    /** The {@code action} value. */
    public static final String ACTION_VALUE = "SMS_EXCHANGE_SENDIND";

    /** The {@link Message} to send. */
    private Message message = null;

    /**
     * Constructor with {@link Message}.
     *
     * @param message
     *            The message to send.
     */
    public SendSMSAction(Message message) {
        this.message = message;
    }

    /**
     * Create the JSON message from the {@link Message}. <br />
     * Send to {@code GTalk} account.
     */
    @Override
    public void execute() {
        JsonObjectBuilder jsonObjectBuilder = Json
                .createObjectBuilder()
                .add(ACTION_KEY, ACTION_VALUE)
                .add(CONVERSATION, message.getConversation().getId())
                .add(PHONE_NUMBER,
                        message.getConversation().getContact().getPhoneNumber()
                        .toString()).add(CONTENT, message.getContent());
        JsonObject jsonObject = jsonObjectBuilder.build();
        String message = jsonObject.toString();

        User.get().getXmppManager().send(message);
    }

    @Override
    public boolean fromSmartphone() {
        return false;
    }

}
