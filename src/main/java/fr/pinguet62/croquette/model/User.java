package fr.pinguet62.croquette.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	    // Messages
	    for (int mess = 1; mess <= 20; mess++) {
		final Message message = new Message();
		message.setContact(contact);
		message.setContent("Contact " + con + " - Message " + mess);
		message.setDate(new Date(2012 - 1900, 9, mess));
		message.setInProgress(mess == 19);
		message.setRead((5 < con) || (mess < 18));
		message.setSent(((int) (2 * Math.random()) % 2) == 0);
		conversation.add(message);
	    }
	}
    }

    public static void set(final User user) {
	User.u = user;
    }

    /** The code. */
    private String code = null;

    /** The {@link Contact}s. */
    private final List<Contact> contacts = new ArrayList<Contact>();

    /** Default constructor. */
    public User() {
    }

    /**
     * Constructor.
     * 
     * @param code
     *            The code.
     */
    public User(final String code) {
	this.code = code;
    }

    /**
     * Gets the code.
     * 
     * @return The code.
     */
    public String getCode() {
	return this.code;
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
     * Sets the code.
     * 
     * @param code
     *            The code to set.
     */
    public void setCode(final String code) {
	this.code = code;
    }

}
