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
     * Add new conversation to the list.<br />
     * The conversation requires at less one {@link Message}.
     * 
     * @param conversation
     *            The {@link Conversation}.
     * @return <code>true</code> if the {@link Conversation} has been insert,
     *         <code>false</code> otherwise.
     */
    @Override
    public boolean add(final Conversation conversation) {
	if (conversation.isEmpty())
	    return false;
	User.get().getContacts().add(conversation.getContact());
	return super.add(conversation);
    }

    /**
     * Find {@link Conversation} into list by id.
     * 
     * @param id
     *            The {@link Conversation} id.
     * @return The corresponding {@link Conversation}, <code>null</code> if not
     *         found.
     */
    public Conversation get(final Integer id) {
	if (id == null)
	    return null;
	for (final Conversation conversation : this)
	    if (conversation.getId() == id)
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
