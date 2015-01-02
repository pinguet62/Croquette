package fr.pinguet62.croquette.webapp.action.sms.exchange;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import com.google.gson.Gson;

import fr.pinguet62.croquette.commons.dto.ReceivedSmsDto;
import fr.pinguet62.croquette.webapp.action.Action;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.bean.SmsManagedBean;
import fr.pinguet62.croquette.webapp.model.Contact;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.Message.State;
import fr.pinguet62.croquette.webapp.model.PhoneNumber;
import fr.pinguet62.croquette.webapp.model.User;

/** SMS received. */
@Action(ReceivedSmsDto.KEY)
public final class ReceivedSMSAction implements IAction {

    /** The {@code action} value. */
    public static final String ACTION_VALUE = "SMS_EXCHANGE_RECEPTION";

    /** The JSON message. */
    private final String json;

    /**
     * Constructor.
     *
     * @param jsonMessage
     *            The JSON message.
     */
    public ReceivedSMSAction(String json) {
        this.json = json;
    }

    /**
     * Create the {@link Message} from the JSON message. <br />
     * Add to {@link Conversation} and update the view.
     */
    @Override
    public void execute() {
        ReceivedSmsDto dto = new Gson().fromJson(json.toString(),
                ReceivedSmsDto.class);

        // Find conversation
        int conversationId = dto.getConversation();
        Conversation conversation = User.get().getConversations()
                .get(conversationId);

        // New conversation
        if (conversation == null) {
            // Find contact
            PhoneNumber phoneNumber = new PhoneNumber(dto.getPhoneNumber());
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
        message.setContent(dto.getContent());
        message.setConversation(conversation);
        message.setDate(dto.getDate());
        message.setId(dto.getId());
        message.setRead(false);
        message.setSent(false);
        message.setState(State.OK);

        conversation.add(message);

        // Update view
        PushContext pushContext = PushContextFactory.getDefault()
                .getPushContext();
        pushContext.push(SmsManagedBean.CHANNEL, null);
    }

    @Override
    public boolean fromSmartphone() {
        return true;
    }

}
