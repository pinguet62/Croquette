package fr.pinguet62.croquette.model;

import java.util.TreeSet;

/**
 * Contains list of {@link Message}s.<br />
 * There are ordered by ascending date.
 */
public final class Conversation extends TreeSet<Message> implements
	Comparable<Conversation> {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = -6466753476994997663L;

    /** The sender or addressee {@link Contact}. */
    private Contact contact = null;

    /** If has old {@link Message}s. */
    private boolean hasOldMessages = true;

    /** The id. */
    private Integer id = null;

    /**
     * The input text of the new {@link Message} into this {@link Conversation}.
     */
    private String input = null;

    /** Change <code>read</code> flag of all {@link Message}s. */
    public void allRead() {
	for (Message message : this)
	    message.setRead(true);
    }

    /**
     * Method used to compare the current {@link Conversation} to an other.
     * 
     * @param other
     *            The other {@link Conversation}.
     * @return A negative integer if the latest {@link Message} of the current
     *         {@link Conversation} is latest than the latest {@link Message} of
     *         the other, zero if there are equal, a positive integer otherwise.
     */
    @Override
    public int compareTo(final Conversation other) {
	return other.first().getDate().compareTo(this.first().getDate());
    }

    /**
     * Gets the sender or addressee {@link Contact}.
     * 
     * @return The {@link Contact}.
     */
    public Contact getContact() {
	return this.contact;
    }

    /**
     * Gets if has old {@link Message}s.
     * 
     * @return Result.
     */
    public boolean getHasOldMessages() {
	return this.hasOldMessages;
    }

    /**
     * Gets the id.
     * 
     * @return The id.
     */
    public Integer getId() {
	return this.id;
    }

    /**
     * Gets the input.
     * 
     * @return The input.
     */
    public String getInput() {
	return this.input;
    }

    /**
     * Gets count of unread {@link Message}s.
     * 
     * @return The count.
     */
    public int getUnreadCount() {
	int count = 0;
	for (Message message : this)
	    if (!message.isRead())
		count++;
	return count;
    }

    /**
     * Sets the sender or addressee {@link Contact}.
     * 
     * @param contact
     *            The {@link Contact}.
     */
    public void setContact(final Contact contact) {
	this.contact = contact;
    }

    /**
     * Sets if has old {@link Message}s.
     * 
     * @param hasOldMessages
     *            If has old {@link Message}s.
     */
    public void setHasOldMessages(final boolean hasOldMessages) {
	this.hasOldMessages = hasOldMessages;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            The id.
     */
    public void setId(final Integer id) {
	this.id = id;
    }

    /**
     * Sets the input.
     * 
     * @param input
     *            The input to set.
     */
    public void setInput(final String input) {
	this.input = input;
    }
}
