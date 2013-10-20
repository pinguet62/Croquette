package fr.pinguet62.croquette.model;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;

import fr.pinguet62.croquette.model.Message.State;

/** Informations about user. */
public final class User {

    private static User u;

    /**
     * Gets the session user.
     * 
     * @return The session user.
     */
    public static User get() {
	return User.u;
	// Authentication authentication = SecurityContextHolder.getContext()
	// .getAuthentication();
	// if ((authentication == null) || !authentication.isAuthenticated())
	// return null;
	// else
	// return (User) SecurityContextHolder.getContext()
	// .getAuthentication().getDetails();
    }

    // TODO Delete this
    @SuppressWarnings("deprecation")
    public static void initTest() {
	// User
	final User user = new User();
	User.set(user);
	// Contacts
	for (int con = 1; con <= 30; ++con) {
	    Contact contact = new Contact();
	    contact.setName("Contact " + con);
	    contact.setPhoneNumber("phoneNumber " + con);
	    user.getContacts().add(contact);
	    // Conversation
	    Conversation conversation = new Conversation();
	    contact.setConversation(conversation);
	    conversation
		    .setHasOldMessages(((int) (2 * Math.random()) % 2) == 0);
	    // Messages
	    for (int mess = 1; mess <= 20; mess++) {
		final Message message = new Message();
		message.setContact(contact);
		message.setContent("Contact " + con + " - Message " + mess);
		message.setDate(new Date(2012 - 1900, 9, mess));
		message.setRead((5 < con) || (mess < 18));
		message.setSent(((int) (2 * Math.random()) % 2) == 0);
		message.setState(State.OK);
		conversation.add(message);
	    }
	}
    }

    public static void set(final User user) {
	User.u = user;
    }

    /** The {@link Contact}s. */
    private final List<Contact> contacts = new ArrayList<Contact>();

    /** The OAuth token. */
    private String token;

    /** Default constructor. */
    public User() {
    }

    /**
     * Download {@link ContactEntry} from Google account with the OAuth token. <br />
     * Pop list of {@link Contact}s of the {@link User}.
     */
    public void downloadGoogleContacts() {
	try {
	    ContactsService contactsService = new ContactsService("Croquette");
	    contactsService.setHeader("Authorization", "Bearer " + this.token);
	    URL feedUrl = new URL(
		    "https://www.google.com/m8/feeds/contacts/default/full");
	    ContactFeed resultFeed = contactsService.getFeed(feedUrl,
		    ContactFeed.class);
	    List<ContactEntry> contactEntries = resultFeed.getEntries();
	    for (ContactEntry contactEntry : contactEntries)
		System.out.println(contactEntry.toString());
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }

    /**
     * Gets {@link Contact} by phone number.
     * 
     * @param phoneNumber
     *            The phone number
     * @return The {@link Contact}, <code>null</code> if not find.
     */
    public Contact getContact(final String phoneNumber) {
	if (phoneNumber == null)
	    return null;
	for (Contact contact : this.contacts)
	    if (phoneNumber.equals(contact.getPhoneNumber()))
		return contact;
	return null;
    }

    /**
     * Gets the list of {@link Contact}s.
     * 
     * @return The list of {@link Contact}s.
     */
    public List<Contact> getContacts() {
	return this.contacts;
    }

    /**
     * Sets the OAuth token.
     * 
     * @param token
     *            The OAuth token to set.
     */
    public void setToken(final String token) {
	this.token = token;
    }

}
