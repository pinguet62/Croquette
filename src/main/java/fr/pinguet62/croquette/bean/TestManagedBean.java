package fr.pinguet62.croquette.bean;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import fr.pinguet62.croquette.model.User;

/** Managed bean used to test some background {@link Action}s in application. */
@ManagedBean(name = "testManagedBean")
@SessionScoped
public final class TestManagedBean {

    /**
     * Invoke {@link LoadedSMSAction}.<br />
     * The number of SMS are chosen randomly.
     * 
     * @param contact
     *            The contact.
     */
    public void loadedSMSAction(final Contact contact) {
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(contact.getConversation().first().getDate());
	calendar.add(Calendar.DATE, -1);

	JsonObjectBuilder baseBuilder = Json.createObjectBuilder()
		.add(IAction.ACTION_KEY, LoadedSMSAction.ACTION_VALUE)
		.add(SMSAction.PHONE_NUMBER, contact.getPhoneNumber());
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
		    .add(LoadedSMSAction.SENT,
			    ((int) (2 * Math.random()) == 0));
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
	List<Contact> contacts = User.get().getContacts();
	Contact contact = contacts.get((int) (Math.random() * contacts.size()));

	JsonObject jsonMessage = Json
		.createObjectBuilder()
		.add(IAction.ACTION_KEY, ReceivedSMSAction.ACTION_VALUE)
		.add(SMSAction.CONTENT, "TestManagedBean.receviedSMSAction()")
		.add(SMSAction.DATE,
			DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
				DateFormat.DEFAULT).format(new Date()))
		.add(SMSAction.PHONE_NUMBER, contact.getPhoneNumber()).build();
	IAction action = ActionFactory.getAction(jsonMessage);
	action.execute();
    }

}
