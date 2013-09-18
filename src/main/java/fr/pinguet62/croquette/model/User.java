package fr.pinguet62.croquette.model;

import java.util.ArrayList;
import java.util.Collection;

/** Informations about user. */
public final class User {

    /** The conversations. */
    private final Conversations conversations = new Conversations();

    /**
     * Gets the list of {@link Contact}.
     * 
     * @return The list of {@link Contact}.
     */
    public Iterable<Contact> getContacts() {
	// TODO Implement User.getContacts()
	final Collection<Contact> contacts = new ArrayList<Contact>();
	for (int i = 1; i < 35; ++i)
	    contacts.add(new Contact("name " + i));
	return contacts;
    }

    /**
     * Gets the {@link Conversations}.
     * 
     * @return The {@link Conversations}.
     */
    public Conversations getConversations() {
	return this.conversations;
    }

}
