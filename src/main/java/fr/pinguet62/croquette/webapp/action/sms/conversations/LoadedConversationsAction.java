package fr.pinguet62.croquette.webapp.action.sms.conversations;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import com.google.gson.Gson;

import fr.pinguet62.croquette.commons.dto.ConversationDto;
import fr.pinguet62.croquette.commons.dto.LoadedConversationsDto;
import fr.pinguet62.croquette.commons.dto.MessageDto;
import fr.pinguet62.croquette.webapp.action.Action;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.bean.SmsManagedBean;
import fr.pinguet62.croquette.webapp.model.Contact;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.Message.State;
import fr.pinguet62.croquette.webapp.model.PhoneNumber;
import fr.pinguet62.croquette.webapp.model.User;

/** Old {@link Conversation}s received. */
@Action(LoadedConversationsDto.KEY)
public final class LoadedConversationsAction implements IAction {

    /** The {@code action} value. */
    public static final String ACTION_VALUE = "SMS_CONVERSATIONS_LOADED";

    /** Key for id of {@link Conversation}. */
    public static final String CONVERSATION = "conversation";

    /** Key for {@link Message} of {@link Conversation}. */
    public static final String CONVERSATION_MESSAGE = "message";

    /** Key for content of {@link Message} of {@link Conversation}. */
    public static final String CONVERSATION_MESSAGE_CONTENT = "content";

    /** Key for date of {@link Message} of {@link Conversation}. */
    public static final String CONVERSATION_MESSAGE_DATE = "date";

    /** Key for id of {@link Message} of {@link Conversation}. */
    public static final String CONVERSATION_MESSAGE_ID = "id";

    /**
     * Key for boolean to define if the {@link Message} of the
     * {@link Conversation} was sent or not.
     */
    public static final String CONVERSATION_MESSAGE_SENT = "sent";

    /** Key for number of {@link Conversation} to load. */
    public static final String CONVERSATIONS = "conversations";

    /** Key for the {@link PhoneNumber} of {@link Contact}. */
    public static final String PHONE_NUMBER = "phone number";

    /** The JSON message. */
    private final String json;

    /**
     * Constructor.
     *
     * @param jsonMessage
     *            The JSON message.
     */
    public LoadedConversationsAction(String json) {
        this.json = json;
    }

    @Override
    public void execute() {
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
        PushContext pushContext = PushContextFactory.getDefault()
                .getPushContext();
        pushContext.push(SmsManagedBean.CHANNEL, null);
    }

    @Override
    public boolean fromSmartphone() {
        return true;
    }

}
