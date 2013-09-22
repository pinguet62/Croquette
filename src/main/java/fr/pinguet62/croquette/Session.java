package fr.pinguet62.croquette;

import java.util.Date;
import java.util.Map;

import javax.faces.context.FacesContext;

import fr.pinguet62.croquette.model.Contact;
import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.model.User;

/** Gets & sets informations into session map. */
public final class Session {

    /** Key for connected user */
    public static final String USER = "user";

    /**
     * Gets session map.
     * 
     * @return The session map.
     */
    private static Map<String, Object> getMap() {
	return FacesContext.getCurrentInstance().getExternalContext()
		.getSessionMap();
    }

    /**
     * Get the {@link User}.
     * 
     * @return The {@link User}.
     */
    public static User getUser() {
	return (User) Session.getMap().get(Session.USER);
    }

    // TODO Delete this
    @SuppressWarnings("deprecation")
    public static void init() {
	// User
	final User user = new User();
	Session.getMap().put(Session.USER, user);
	// -> Contacts
	for (int con = 1; con < 35; ++con) {
	    final Contact contact = new Contact();
	    contact.setName("Contact " + con);
	    contact.setPhoneNumber("phoneNumber " + con);
	    user.getContacts().add(contact);
	    // Conversation
	    Conversation conversation = new Conversation();
	    conversation.setContact(contact);
	    // -> Messages
	    for (int mess = 1; mess < 20; mess++) {
		final Message message = new Message();
		message.setContact(contact);
		message.setContent("Contact " + con + " - Message " + mess);
		message.setDate(new Date(2013, 9, mess));
		message.setInProgress(mess == 19);
		message.setSent(((int) (2 * Math.random()) % 2) == 0);
		conversation.add(message);
	    }
	    user.getConversations().add(conversation);
	}
    }
}
