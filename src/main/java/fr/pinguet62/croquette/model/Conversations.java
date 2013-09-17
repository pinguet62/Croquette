package fr.pinguet62.croquette.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/** Contains list of {@link Conversation}. */
public final class Conversations implements Iterable<Conversation> {

    /** List of {@link Conversation}s. */
    private final Collection<Conversation> conversations = new HashSet<Conversation>();

    /**
     * Find {@link Conversation} into list of conversations.
     * 
     * @param phoneNumber
     *            The phone nomber of {@link Conversation}.
     * @return The corresponding {@link Conversation}.
     */
    public Conversation get(final String phoneNumber) {
	if (phoneNumber == null)
	    return null;
	for (final Conversation conversation : this.conversations)
	    if (phoneNumber.equals(conversation.getContact().getPhoneNumber()))
		return conversation;
	return null;
    }

    /**
     * Gets an iterator over the list of {@link Conversation}s.
     * 
     * @return An {@link Iterator}.
     */
    @Override
    public Iterator<Conversation> iterator() {
	return this.conversations.iterator();
    }

}
