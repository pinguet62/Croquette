package fr.pinguet62.croquette.model;

import java.util.Collection;
import java.util.TreeSet;

/**
 * Contains list of {@link Message}s.<br />
 * There are ordered by reverse chronological order.
 */
public final class Conversation extends TreeSet<Message> implements
Comparable<Conversation> {

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    /** The sender or addressee {@link Contact}. */
    private Contact contact;

    /** If has old {@link Message}s. */
    private boolean hasOldMessages = true;

    /** The id. */
    private Integer id;

    /**
     * The input text of the new {@link Message} into this {@link Conversation}.
     */
    private String input;

    /**
     * Add new {@link Message} to the list.
     *
     * @param message
     *            The {@link Message}.
     * @return {@code true} if the {@link Message} has been insert,
     *         {@code false} otherwise.
     */
    @Override
    public boolean add(Message message) {
	if (!message.isRead())
	    hasOldMessages = true;
	return super.add(message);
    }

    /**
     * Add all {@link Message}s to the list.
     *
     * @param messages
     *            The {@link Message}s.
     * @return {@code true} if all {@link Message}s have been insert,
     *         {@code false} otherwise.
     */
    @Override
    public boolean addAll(Collection<? extends Message> messages) {
	boolean allInserted = true;
	for (Message message : messages)
	    if (!add(message))
		allInserted = false;
	return allInserted;
    }

    /** Change {@link Message#read} flag of all {@link Message}s. */
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
    public int compareTo(Conversation other) {
	if (isEmpty() && other.isEmpty())
	    return getContact().compareTo(other.getContact());
	else if (isEmpty())
	    return +1; // TODO -1 ?
	else if (other.isEmpty())
	    return -1; // TODO +1 ?
	return other.first().getDate().compareTo(first().getDate());
    }

    /**
     * Gets the sender or addressee {@link Contact}.
     *
     * @return The {@link Contact}.
     */
    public Contact getContact() {
	return contact;
    }

    /**
     * Gets if has old {@link Message}s.
     *
     * @return Result.
     */
    public boolean getHasOldMessages() {
	return hasOldMessages;
    }

    /**
     * Gets the id.
     *
     * @return The id.
     */
    public Integer getId() {
	return id;
    }

    /**
     * Gets the input.
     *
     * @return The input.
     */
    public String getInput() {
	return input;
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
    public void setContact(Contact contact) {
	this.contact = contact;
    }

    /**
     * Sets if has old {@link Message}s.
     *
     * @param hasOldMessages
     *            If has old {@link Message}s.
     */
    public void setHasOldMessages(boolean hasOldMessages) {
	this.hasOldMessages = hasOldMessages;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            The id.
     */
    public void setId(Integer id) {
	this.id = id;
    }

    /**
     * Sets the input.
     *
     * @param input
     *            The input to set.
     */
    public void setInput(String input) {
	this.input = input;
    }

}
