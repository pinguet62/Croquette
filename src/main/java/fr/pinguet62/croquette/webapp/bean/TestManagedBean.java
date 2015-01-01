package fr.pinguet62.croquette.webapp.bean;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import fr.pinguet62.croquette.webapp.action.Action;
import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.action.sms.conversation.LoadedSMSAction;
import fr.pinguet62.croquette.webapp.action.sms.conversation.LoadingSMSAction;
import fr.pinguet62.croquette.webapp.action.sms.exchange.ExchangeSMSAction;
import fr.pinguet62.croquette.webapp.action.sms.exchange.ReceivedSMSAction;
import fr.pinguet62.croquette.webapp.model.Contact;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.PhoneNumber;
import fr.pinguet62.croquette.webapp.model.User;
import fr.pinguet62.croquette.webapp.model.Message.State;

/** Managed bean used to test some background {@link Action}s in application. */
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
        int conversationId = (int) (Math.random() * 25);
        JsonArrayBuilder jsonMessages = Json.createArrayBuilder();
        for (int i = 0; i < LoadingSMSAction.COUNT_VALUE; i++) {
            int messageId = (int) (Math.random() * 100);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_YEAR, -(int) (Math.random() * 365));
            System.out.println(calendar.getTime());
            jsonMessages.add(Json
                    .createObjectBuilder()
                    .add(LoadedSMSAction.MESSAGE_ID, messageId)
                    .add(LoadedSMSAction.MESSAGE_SENT,
                            (Math.random() < 0.5 ? Boolean.TRUE.toString()
                                    : Boolean.FALSE.toString()))
                    .add(LoadedSMSAction.MESSAGE_DATE,
                            new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss")
                                    .format(new Date()))
                    .add(LoadedSMSAction.MESSAGE_CONTENT,
                            String.format("Conversation %d, message %d",
                                    conversationId, messageId)));
        }
        String jsonMessage = Json.createObjectBuilder()
                .add(IAction.ACTION_KEY, IAction.ACTION_KEY)
                .add(LoadedSMSAction.CONVERSATION, conversationId)
                .add(LoadedSMSAction.MESSAGES, jsonMessages).build().toString();
        User.get().getXmppManager().send(jsonMessage);
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
        String jsonMessage = Json
                .createObjectBuilder()
                .add(IAction.ACTION_KEY, ReceivedSMSAction.ACTION_VALUE)
                .add(ReceivedSMSAction.ID, messageId)
                .add(ExchangeSMSAction.CONVERSATION, conversationId)
                .add(ExchangeSMSAction.DATE,
                        new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ss")
                                .format(new Date()))
                .add(ExchangeSMSAction.CONTENT,
                        String.format("Conversation %d, message %d",
                                conversationId, messageId)).build().toString();
        User.get().getXmppManager().send(jsonMessage);
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
