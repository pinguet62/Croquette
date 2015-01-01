package fr.pinguet62.croquette.webapp.action.sms.conversations;

import java.text.ParseException;
import java.util.List;

import javax.json.JsonObject;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import fr.pinguet62.croquette.webapp.action.Action;
import fr.pinguet62.croquette.webapp.action.ActionException;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.bean.SmsManagedBean;
import fr.pinguet62.croquette.webapp.model.Contact;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.PhoneNumber;
import fr.pinguet62.croquette.webapp.model.User;
import fr.pinguet62.croquette.webapp.model.Message.State;

/** Old {@link Conversation}s received. */
@Action(LoadedConversationsAction.ACTION_VALUE)
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
    private final JsonObject jsonMessage;

    /**
     * Constructor.
     *
     * @param jsonMessage
     *            The JSON message.
     */
    public LoadedConversationsAction(JsonObject jsonMessage) {
        this.jsonMessage = jsonMessage;
    }

    @Override
    public void execute() {
        try {
            // Each conversation
            List<JsonObject> jsonConversations = jsonMessage.getJsonArray(
                    CONVERSATIONS).getValuesAs(JsonObject.class);
            for (JsonObject conversationJson : jsonConversations) {
                // Find conversation
                int conversationId = conversationJson.getInt(CONVERSATION);
                Conversation conversation = User.get().getConversations()
                        .get(conversationId);

                // New conversation
                if (conversation == null) {
                    // Find contact
                    PhoneNumber phoneNumber = new PhoneNumber(
                            conversationJson.getString(PHONE_NUMBER));
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
                JsonObject messageJson = conversationJson
                        .getJsonObject(CONVERSATION_MESSAGE);
                Message message = new Message();
                message.setContent(messageJson
                        .getString(CONVERSATION_MESSAGE_CONTENT));
                message.setConversation(conversation);
                message.setDate(FORMATTER.parse(messageJson
                        .getString(CONVERSATION_MESSAGE_DATE)));
                message.setId(messageJson.getInt(CONVERSATION_MESSAGE_ID));
                message.setRead(true);
                message.setSent(messageJson
                        .getBoolean(CONVERSATION_MESSAGE_SENT));
                message.setState(State.OK);

                conversation.add(message);
            }
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
