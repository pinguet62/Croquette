package fr.pinguet62.croquette.model;

import java.util.ArrayList;
import java.util.List;

/** Informations about user. */
public final class User {

    /** The contacts. */
    private final List<Contact> contacts = new ArrayList<Contact>();

    /** The conversations. */
    private final Conversations conversations = new Conversations();

    /**
     * Gets the list of {@link Contact}.
     * 
     * @return The list of {@link Contact}.
     */
    public List<Contact> getContacts() {
	return this.contacts;
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
