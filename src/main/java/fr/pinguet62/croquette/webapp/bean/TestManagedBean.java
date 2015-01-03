package fr.pinguet62.croquette.webapp.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import com.google.gson.Gson;

import fr.pinguet62.croquette.commons.dto.LoadedConversationDto;
import fr.pinguet62.croquette.commons.dto.LoadingConversationDto;
import fr.pinguet62.croquette.commons.dto.MessageDto;
import fr.pinguet62.croquette.commons.dto.ReceivedSmsDto;
import fr.pinguet62.croquette.webapp.action.SmartphoneHandler;
import fr.pinguet62.croquette.webapp.action.sms.conversation.LoadedSMSAction;
import fr.pinguet62.croquette.webapp.action.sms.exchange.ReceivedSMSAction;
import fr.pinguet62.croquette.webapp.model.Contact;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.Message.State;
import fr.pinguet62.croquette.webapp.model.PhoneNumber;
import fr.pinguet62.croquette.webapp.model.User;

/** Managed bean used to test some background {@link SmartphoneHandler}s in application. */
@ManagedBean(name = "testManagedBean")
@SessionScoped
public final class TestManagedBean {

    private String attribute;

    private String xmppMessage;

    /** Download Google contacts */
    public void downloadGoogleContacts() {
        User.get().getContacts().downloadGoogle();
    }

    public String getAttribute() {
        return attribute;
    }

    /**
     * Gets the xmppMessage.
     *
     * @return The xmppMessage.
     */
    public String getXmppMessage() {
        return xmppMessage;
    }

    /** Initialize application with test data. */
    public void initTestData() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.set(2012, 0, 0);
        User user = User.get();
        // Conversation
        for (int conv = 1; conv <= 40; ++conv) {
            Conversation conversation = new Conversation();
            conversation.setHasOldMessages(Math.random() < 0.5);
            conversation.setId(conv);
            // Contact
            Contact contact = new Contact();
            contact.setName("Contact " + conv);
            contact.setPhoneNumber(new PhoneNumber("phoneNumber " + conv));
            conversation.setContact(contact);
            // Messages
            for (int mess = 1; mess <= 20; ++mess) {
                final Message message = new Message();
                message.setContent("Contact " + conv + " - Message " + mess);
                message.setConversation(null); // TODO
                message.setDate(calendar.getTime());
                calendar.add(Calendar.DAY_OF_YEAR, 1);
                message.setId((100 * conv) + mess);
                message.setRead((conv < 35) || (mess < 15));
                message.setSent(Math.random() < 0.5);
                message.setState(State.OK);
                conversation.add(message);
            }
            user.getConversations().add(conversation);
        }
    }

    /**
     * Invoke {@link LoadedSMSAction}.<br />
     * The number of SMS are chosen randomly.
     *
     * @param contact
     *            The contact.
     */
    public void loadedSMSAction(final Conversation conversation) {
        LoadedConversationDto conversationDto = new LoadedConversationDto();
        int conversationId = (int) (Math.random() * 25);
        conversationDto.setConversation(conversationId);
        for (int i = 0; i < LoadingConversationDto.COUNT; i++) {
            int messageId = (int) (Math.random() * 100);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -(int) (Math.random() * 365));
            MessageDto messageDto = new MessageDto();
            messageDto.setContent(String.format("Conversation %d, message %d",
                    conversationId, messageId));
            messageDto.setDate(new Date());
            messageDto.setId(messageId);
            messageDto.setSent(Math.random() < 0.5 ? true : false);
            conversationDto.getMessages().add(messageDto);
        }
        String json = new Gson().toJson(conversationDto);
        User.get().getXmppManager().send(json);
    }

    /** Test for Prime Push messages. */
    public void primePush() {
        PushContext pushContext = PushContextFactory.getDefault()
                .getPushContext();
        pushContext.push(SmsManagedBean.CHANNEL, null);
    }

    /**
     * Invoke {@link ReceivedSMSAction}.<br />
     * The {@link Contact} is chosen randomly.
     */
    public void receviedSMSAction() {
        int messageId = (int) Math.random() * 100;
        int conversationId = (int) Math.random() * 25;
        ReceivedSmsDto receivedSmsDto = new ReceivedSmsDto();
        receivedSmsDto.setContent(String.format("Conversation %d, message %d",
                conversationId, messageId));
        receivedSmsDto.setConversation(conversationId);
        receivedSmsDto.setDate(new Date());
        receivedSmsDto.setId(messageId);
        receivedSmsDto.setPhoneNumber(UUID.randomUUID().toString());
        String json = new Gson().toJson(receivedSmsDto);
        User.get().getXmppManager().send(json);
    }

    /** Send XMPP message. */
    public void sendXmppMessage() {
        User.get().getXmppManager().send(xmppMessage);
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * Sets the xmppMessage.
     *
     * @param xmppMessage
     *            The xmppMessage.
     */
    public void setXmppMessage(String xmppMessage) {
        this.xmppMessage = xmppMessage;
    }

}
