package fr.pinguet62.croquette.webapp.action.sms.exchange;

import java.text.ParseException;

import javax.json.JsonObject;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import fr.pinguet62.croquette.webapp.action.Action;
import fr.pinguet62.croquette.webapp.action.ActionException;
import fr.pinguet62.croquette.webapp.bean.SmsManagedBean;
import fr.pinguet62.croquette.webapp.model.Contact;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.PhoneNumber;
import fr.pinguet62.croquette.webapp.model.User;
import fr.pinguet62.croquette.webapp.model.Message.State;

/** SMS received. */
@Action(ReceivedSMSAction.ACTION_VALUE)
public final class ReceivedSMSAction extends ExchangeSMSAction {

    /** The {@code action} value. */
    public static final String ACTION_VALUE = "SMS_EXCHANGE_RECEPTION";

    /** Key for the id of the {@link Message}. */
    public static final String ID = "id";

    /** The JSON message. */
    private final JsonObject jsonMessage;

    /**
     * Constructor.
     *
     * @param jsonMessage
     *            The JSON message.
     */
    public ReceivedSMSAction(JsonObject jsonMessage) {
        this.jsonMessage = jsonMessage;
    }

    /**
     * Create the {@link Message} from the JSON message. <br />
     * Add to {@link Conversation} and update the view.
     */
    @Override
    public void execute() {
        try {
            // Find conversation
            int conversationId = jsonMessage.getInt(CONVERSATION);
            Conversation conversation = User.get().getConversations()
                    .get(conversationId);

            // New conversation
            if (conversation == null) {
                // Find contact
                PhoneNumber phoneNumber = new PhoneNumber(
                        jsonMessage.getString(PHONE_NUMBER));
                Contact contact = User.get().getContacts().get(phoneNumber);

                // New contact
                if (contact == null) {
                    // Build contact
                    contact = new Contact();
                    contact.setName(phoneNumber.toString());
                    contact.setPhoneNumber(phoneNumber);
                    User.get().getContacts().add(contact);
                }

                // Build conversation
                conversation = new Conversation();
                conversation.setContact(contact);
                conversation.setId(conversationId);
                User.get().getConversations().add(conversation);
            }

            // Build message
            Message message = new Message();
            message.setContent(jsonMessage.getString(CONTENT));
            message.setConversation(conversation);
            message.setDate(FORMATTER.parse(jsonMessage.getString(DATE)));
            message.setId(jsonMessage.getInt(ID));
            message.setRead(false);
            message.setSent(false);
            message.setState(State.OK);

            conversation.add(message);
        } catch (ParseException | NullPointerException exception) {
            throw new ActionException(exception);
        }

        PushContext pushContext = PushContextFactory.getDefault()
                .getPushContext();
        pushContext.push(SmsManagedBean.CHANNEL, null);
    }

    @Override
    public boolean fromSmartphone() {
        return true;
    }

}
