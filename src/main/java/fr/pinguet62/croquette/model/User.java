package fr.pinguet62.croquette.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;

/** Informations about user. */
public final class User {

    /**
     * Gets the session user.
     * 
     * @return The session user.
     */
    public static User get() {
	return (User) SecurityContextHolder.getContext().getAuthentication()
		.getPrincipal();
    }

    public static void set(final User user) {
	SecurityContextHolder.getContext().setAuthentication(null);
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

    // /**
    // * Gets the {@link Conversations}.
    // *
    // * @return The {@link Conversations}.
    // */
    // public Conversations getConversations() {
    // Conversations conversations = new Conversations();
    // for (Contact contact : this.contacts)
    // conversations.add(contact.getConversation());
    // return conversations;
    // }

}
