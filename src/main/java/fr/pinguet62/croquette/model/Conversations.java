package fr.pinguet62.croquette.model;

import java.util.Collection;
import java.util.TreeSet;

/**
 * Contains list of {@link Conversation}s. <br />
 * There are ordered by descending date of the latest {@link Message}.
 */
public final class Conversations extends TreeSet<Conversation> {

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    /** If has old {@link Conversation}s. */
    private boolean hasOldConversations = true;

    /**
     * Add new {@link Conversation} to the list.<br />
     * The {@link Conversation} requires at less one {@link Message}.
     *
     * @param conversation
     *            The {@link Conversation}.
     * @return {@code true} if the {@link Conversation} has been insert,
     *         {@code false} otherwise.
     */
    @Override
    public boolean add(Conversation conversation) {
	if (conversation.isEmpty())
	    return false;
	// User.get().getContacts().add(conversation.getContact());
	return super.add(conversation);
    }

    /**
     * Add all {@link Conversation}s to the list.<br />
     * The {@link Conversation}s requires at less one {@link Message}.
     *
     * @param conversations
     *            The {@link Conversation}s.
     * @return {@code true} if all {@link Conversation}s have been insert,
     *         {@code false} otherwise.
     */
    @Override
    public boolean addAll(Collection<? extends Conversation> conversations) {
	boolean res = true;
	for (Conversation conversation : conversations)
	    if (conversation.isEmpty())
		res = false;
	    else
		add(conversation); // TODO error
	return res;
    }

    /**
     * Find {@link Conversation} into list by id.
     *
     * @param id
     *            The {@link Conversation} id.
     * @return The corresponding {@link Conversation}, {@code null} if not
     *         found.
     */
    public Conversation get(Integer id) {
	if (id == null)
	    return null;
	for (Conversation conversation : this)
	    if (conversation.getId().equals(id))
		return conversation;
	return null;
    }

    /**
     * Gets if has old {@link Conversation}s.
     *
     * @return Result.
     */
    public boolean getHasOldMessages() {
	return hasOldConversations;
    }

    /**
     * Sets if has old {@link Conversation}s.
     *
     * @param hasOldConversations
     *            If has old {@link Conversation}s.
     */
    public void setHasOldMessages(boolean hasOldConversations) {
	this.hasOldConversations = hasOldConversations;
    }

}
