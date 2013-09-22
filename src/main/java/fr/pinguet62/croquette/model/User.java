package fr.pinguet62.croquette.model;

import java.util.ArrayList;
import java.util.List;

/** Informations about user. */
public final class User {

    /** The {@link Contact}s. */
    private final List<Contact> contacts = new ArrayList<Contact>();

    /**
     * Gets the list of {@link Contact}s.
     * 
     * @return The list of {@link Contact}s.
     */
    public List<Contact> getContacts() {
	return this.contacts;
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
