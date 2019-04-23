package fr.pinguet62.croquette.webapp.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.TreeSet;

/** There are ordered by reverse chronological order. */
public final class Conversation extends TreeSet<Message> implements
        Serializable, Comparable<Conversation> {

    private static final long serialVersionUID = 1;

    private Contact contact;

    /** By default {@link Message}s are not all loaded. */
    private boolean hasOldMessages = true;

    private Integer id;

    /** The input text of next {@link Message}. */
    private String input;

    public Conversation() {
    }

    public Conversation(int id, Contact contact) {
        this.id = id;
        this.contact = contact;
    }

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
        boolean res = super.add(message);
        // User.get().getConversations().sort(); // TODO
        return res;
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

    /**
     * Change {@link Message#read} flag of all {@link Message}s.
     *
     * @see Message#read
     */
    public void allRead() {
        for (Message message : this)
            message.setRead(true);
    }

    // TODO better implementation
    @Override
    public int compareTo(Conversation other) {
        if (isEmpty() && other.isEmpty())
            return getContact().compareTo(other.getContact());
        else if (isEmpty())
            return +1; // TODO -1 ?
        else if (other.isEmpty())
            return -1; // TODO +1 ?
        return other.last().getDate().compareTo(last().getDate());
    }

    public Contact getContact() {
        return contact;
    }

    public boolean getHasOldMessages() {
        return hasOldMessages;
    }

    public Integer getId() {
        return id;
    }

    public String getInput() {
        return input;
    }

    /**
     * Get count of unread {@link Message}s.
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

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setHasOldMessages(boolean hasOldMessages) {
        this.hasOldMessages = hasOldMessages;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "Conv " + id + ": " + super.toString();
    }

}
