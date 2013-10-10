package fr.pinguet62.croquette.bean;

import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import fr.pinguet62.croquette.action.sms.ReceivedSMSAction;
import fr.pinguet62.croquette.model.Contact;
import fr.pinguet62.croquette.model.Message;
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

	Message message = new Message();
	message.setContact(contact);
	message.setContent("test");
	message.setDate(new Date());
	message.setSent(true);
	contact.getConversation().add(message);

	PushContext pushContext = PushContextFactory.getDefault()
		.getPushContext();
	pushContext.push(SmsManagedBean.CHANNEL, null);
    }

}
