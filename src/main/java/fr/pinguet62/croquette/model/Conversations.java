package fr.pinguet62.croquette.model;

import java.util.TreeSet;

/**
 * Contains list of {@link Conversation}s.
 * <p>
 * Extends {@link TreeSet} to sort and add some methods.
 */
public final class Conversations extends TreeSet<Conversation> {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = -8126077649445737099L;

    /**
     * Find {@link Conversation} into list of conversations.
     * 
     * @param phoneNumber
     *            The phone number of {@link Conversation}.
     * @return The corresponding {@link Conversation}.
     */
    public Conversation get(final String phoneNumber) {
	if (phoneNumber == null)
	    return null;
	for (final Conversation conversation : this)
	    if (phoneNumber.equals(conversation.getContact().getPhoneNumber()))
		return conversation;
	return null;
    }

}
