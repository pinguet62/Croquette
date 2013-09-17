package fr.pinguet62.croquette.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Conversation with a {@link Contact}.
 * 
 * @author Pinguet62
 */
public final class Conversation implements Iterable<Message> {

    /** The sender or addressee {@link Contact}. */
    private Contact contact = null;

    /** The list of {@link Message}s. */
    private final Collection<Message> messages = new ArrayList<Message>();

    /**
     * Gets the sender or addressee {@link Contact}.
     * 
     * @return The {@link Contact}.
     * @author Pinguet62
     */
    public Contact getContact() {
	return this.contact;
    }

    /**
     * Gets the list of {@link Message}s.
     * 
     * @return The {@link Message}s.
     * @author Pinguet62
     */
    public Collection<Message> getMessages() {
	return this.messages;
    }

    /**
     * Gets an iterator over the list of {@link Message}s.
     * 
     * @return An {@link Iterator}.
     */
    @Override
    public Iterator<Message> iterator() {
	return this.messages.iterator();
    }

    /**
     * Sets the sender or addressee {@link Contact}.
     * 
     * @param contact
     *            The {@link Contact}.
     * @author Pinguet62
     */
    public void setContact(final Contact contact) {
	this.contact = contact;
    }

}
