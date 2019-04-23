package fr.pinguet62.croquette.webapp.action.sms.exchange;

import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import fr.pinguet62.croquette.commons.action.IAction;
import fr.pinguet62.croquette.commons.dto.MessageDto;
import fr.pinguet62.croquette.commons.dto.ReceivedSmsDto;
import fr.pinguet62.croquette.webapp.action.SmartphoneHandler;
import fr.pinguet62.croquette.webapp.bean.PushResource;
import fr.pinguet62.croquette.webapp.model.Contact;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.Message.State;
import fr.pinguet62.croquette.webapp.model.PhoneNumber;
import fr.pinguet62.croquette.webapp.model.User;

/**
 * Result for {@link SendSMSAction} action.
 *
 * @see SendSMSAction
 * @see ReceivedSmsDto
 */
@SmartphoneHandler(ReceivedSmsDto.KEY)
public final class ReceivedSMSAction implements IAction {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ReceivedSMSAction.class);

    private final String json;

    public ReceivedSMSAction(String json) {
        this.json = json;
    }

    /**
     * <ul>
     * <li>Get (or create if no exists) {@link Conversation}</li>
     * <li>Convert {@link MessageDto} to {@link Message}</li>
     * <li>Update view</li>
     * </ul>
     */
    @Override
    public void execute() {
        LOGGER.info("Action: " + ReceivedSmsDto.KEY);

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
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(PushResource.CHANNEL, null);
    }

}
