package fr.pinguet62.croquette.model;

import java.util.HashSet;

/**
 * Contains list of {@link Conversation}s. <br />
 * There are ordered by descending date of the latest {@link Message}.
 */
public final class Conversations extends HashSet<Conversation> {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = -8126077649445737099L;

    /** If has old {@link Conversation}s. */
    private boolean hasOldConversations = true;

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

    /**
     * Gets if has old {@link Conversation}s.
     * 
     * @return Result.
     */
    public boolean getHasOldMessages() {
	return this.hasOldConversations;
    }

    /**
     * Sets if has old {@link Conversation}s.
     * 
     * @param hasOldConversations
     *            If has old {@link Conversation}s.
     */
    public void setHasOldMessages(final boolean hasOldConversations) {
	this.hasOldConversations = hasOldConversations;
    }

}
