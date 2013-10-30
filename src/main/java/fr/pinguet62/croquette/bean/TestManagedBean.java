package fr.pinguet62.croquette.bean;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import fr.pinguet62.croquette.action.ActionFactory;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.action.sms.LoadedSMSAction;
import fr.pinguet62.croquette.action.sms.LoadindSMSAction;
import fr.pinguet62.croquette.action.sms.ReceivedSMSAction;
import fr.pinguet62.croquette.action.sms.SMSAction;
import fr.pinguet62.croquette.model.Contact;
import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Conversations;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.model.Message.State;
import fr.pinguet62.croquette.model.User;

/** Managed bean used to test some background {@link Action}s in application. */
@ManagedBean(name = "testManagedBean")
@SessionScoped
public final class TestManagedBean {

    /** Download Google contacts */
    public void downloadGoogleContacts() {
	User.get().getContacts().downloadGoogle();
    }

    /** Initialize application with test data. */
    public void initTestData() {
	Calendar calendar = Calendar.getInstance();
	calendar.set(2012, 0, 0);
	User user = User.get();
	// Conversation
	for (int conv = 8; conv <= 38; ++conv) {
	    Conversation conversation = new Conversation();
	    conversation
		    .setHasOldMessages(((int) (2 * Math.random()) % 2) == 0);
	    conversation.setId(conv);
	    // Contact
	    Contact contact = new Contact();
	    contact.setName("Contact " + conv);
	    contact.setPhoneNumber("phoneNumber " + conv);
	    conversation.setContact(contact);
	    // Messages
	    for (int mess = 1; mess <= 20; ++mess) {
		final Message message = new Message();
		message.setContent("Contact " + conv + " - Message " + mess);
		message.setConversation(null); // TODO
		message.setDate(calendar.getTime());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		message.setRead((conv < 35) || (mess < 15));
		message.setSent(((int) (2 * Math.random()) % 2) == 0);
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
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(conversation.first().getDate());
	calendar.add(Calendar.DATE, -1);

	JsonObjectBuilder baseBuilder = Json
		.createObjectBuilder()
		.add(IAction.ACTION_KEY, LoadedSMSAction.ACTION_VALUE)
		.add(SMSAction.PHONE_NUMBER,
			conversation.getContact().getPhoneNumber());
	JsonArrayBuilder messagesBuilder = Json.createArrayBuilder();
	int nbMessages = ((int) (2 * Math.random()) + LoadindSMSAction.COUNT_VALUE) - 1;
	for (int i = 0; i < nbMessages; i++) {
	    calendar.add(Calendar.DATE, -1);
	    JsonObjectBuilder messageBuilder = Json
		    .createObjectBuilder()
		    .add(SMSAction.CONTENT,
			    "TestManagedBean.loadedSMSAction(Contact)")
		    .add(SMSAction.DATE,
			    DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
				    DateFormat.DEFAULT).format(
				    calendar.getTime()))
		    .add(LoadedSMSAction.SENT, ((int) (2 * Math.random()) == 0));
	    messagesBuilder.add(messageBuilder);
	}
	baseBuilder.add(LoadedSMSAction.MESSAGES, messagesBuilder);
	JsonObject jsonMessage = baseBuilder.build();
	IAction action = ActionFactory.getAction(jsonMessage);
	action.execute();
    }

    /**
     * Invoke {@link ReceivedSMSAction}.<br />
     * The {@link Contact} is chosen randomly.
     */
    public void receviedSMSAction() {
	String phoneNumber = null;
	if ((int) ((2 * Math.random()) % 2) == 0)
	    phoneNumber = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
		    DateFormat.DEFAULT).format(new Date());
	else {
	    Conversations conversations = User.get().getConversations();
	    Conversation conversation = null;
	    Iterator<Conversation> it = conversations.iterator();
	    for (int i = 1; i < (int) (Math.random() * conversations.size()); i++)
		conversation = it.next();
	    phoneNumber = conversation.getContact().getPhoneNumber();
	}

	JsonObject jsonMessage = Json
		.createObjectBuilder()
		.add(IAction.ACTION_KEY, ReceivedSMSAction.ACTION_VALUE)
		.add(SMSAction.CONTENT, "TestManagedBean.receviedSMSAction()")
		.add(SMSAction.DATE,
			DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
				DateFormat.DEFAULT).format(new Date()))
		.add(SMSAction.PHONE_NUMBER, phoneNumber).build();
	IAction action = ActionFactory.getAction(jsonMessage);
	action.execute();
    }

}
