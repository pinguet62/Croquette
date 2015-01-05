package fr.pinguet62.croquette.webapp.action.sms.conversations;

import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import fr.pinguet62.croquette.commons.dto.ConversationDto;
import fr.pinguet62.croquette.commons.dto.LoadedConversationsDto;
import fr.pinguet62.croquette.commons.dto.MessageDto;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.action.SmartphoneHandler;
import fr.pinguet62.croquette.webapp.bean.PushResource;
import fr.pinguet62.croquette.webapp.model.Contact;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.Message.State;
import fr.pinguet62.croquette.webapp.model.PhoneNumber;
import fr.pinguet62.croquette.webapp.model.User;

/**
 * Result of {@link LoadingConversationsAction} action.
 *
 * @see LoadingConversationsAction
 * @see LoadedConversationsDto
 */
@SmartphoneHandler(LoadedConversationsDto.KEY)
public final class LoadedConversationsAction implements IAction {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(LoadedConversationsDto.class);

    private final String json;

    public LoadedConversationsAction(String json) {
        this.json = json;
    }

    /**
     * <ul>
     * <li>For each {@link ConversationDto}:</li>
     * <ul>
     * <li>Get (or create if no exists) {@link Conversation}</li>
     * <li>For each {@link MessageDto}</li>
     * <ul>
     * <li>Convert {@link MessageDto} to {@link Message}</li>
     * </ul>
     * </ul><li>Update view</li></ul>
     */
    @Override
    public void execute() {
        LOGGER.info("Action: " + LoadedConversationsDto.KEY);

        LoadedConversationsDto dto = new Gson().fromJson(json.toString(),
                LoadedConversationsDto.class);

        // Each conversation
        for (ConversationDto conversationDto : dto.getConversations()) {
            // Find conversation
            int conversationId = conversationDto.getConversation();
            Conversation conversation = User.get().getConversations()
                    .get(conversationId);

            // New conversation
            if (conversation == null) {
                // Find contact
                PhoneNumber phoneNumber = new PhoneNumber(
                        conversationDto.getPhoneNumber());
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

                // Each message
                for (MessageDto messageDto : conversationDto.getMessages()) {
                    // Build message
                    Message message = new Message();
                    message.setContent(messageDto.getContent());
                    message.setConversation(conversation);
                    message.setDate(messageDto.getDate());
                    message.setId(messageDto.getId());
                    message.setRead(true);
                    message.setSent(messageDto.getSent());
                    message.setState(State.OK);

                    conversation.add(message);
                }
            }
        }

        // Update view
        EventBus eventBus = EventBusFactory.getDefault().eventBus();
        eventBus.publish(PushResource.CHANNEL, null);
    }

}
