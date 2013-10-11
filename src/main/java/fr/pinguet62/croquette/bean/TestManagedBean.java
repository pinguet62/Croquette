package fr.pinguet62.croquette.bean;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.json.Json;
import javax.json.JsonObject;

import fr.pinguet62.croquette.action.ActionFactory;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.action.sms.ReceivedSMSAction;
import fr.pinguet62.croquette.action.sms.SMSAction;
import fr.pinguet62.croquette.model.Contact;
import fr.pinguet62.croquette.model.User;

/** Managed bean used to test some background {@link Action}s in application. */
@ManagedBean(name = "testManagedBean")
@SessionScoped
public final class TestManagedBean {

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
