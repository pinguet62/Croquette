package fr.pinguet62.croquette.model;

import java.io.Serializable;

/** Contains informations about a contact. */
public final class Contact implements Comparable<Contact>, Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    /** The {@link Conversation}. */
    private Conversation conversation;

    /** The name. */
    private String name;

    /** The {@link PhoneNumber}. */
    private PhoneNumber phoneNumber;

    /**
     * Method used to sort {@link Contact}s by {@code name}.<br />
     * Sort by {@link PhoneNumber} if they are the same {@code name}.
     *
     * @param other
     *            The other {@link Contact}.
     * @return A negative integer if this current {@link Contact} is before the
     *         other, zero if there are equal, a positive integer otherwise.
     */
    @Override
    public int compareTo(Contact other) {
	int nameCompare = name.compareTo(other.name);
	if (nameCompare != 0)
	    return nameCompare;
	return phoneNumber.get().compareTo(other.phoneNumber.get());
    }

    /**
     * Gets the {@link Conversation}.
     *
     * @return The {@link Conversation}.
     */
    public Conversation getConversation() {
	return conversation;
    }

    /**
     * Gets the name.
     *
     * @return The name.
     */
    public String getName() {
	return name;
    }

    /**
     * Gets the {@link PhoneNumber}.
     *
     * @return The {@link PhoneNumber}.
     */
    public PhoneNumber getPhoneNumber() {
	return phoneNumber;
    }

    /**
     * Sets the {@link Conversation}.
     *
     * @param conversation
     *            The {@link Conversation} to set.
     */
    public void setConversation(Conversation conversation) {
	this.conversation = conversation;
    }

    /**
     * Sets the name.
     *
     * @param name
     *            The name to set.
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * Sets the {@link PhoneNumber}.
     *
     * @param phoneNumber
     *            The {@link PhoneNumber} to set.
     */
    public void setPhoneNumber(PhoneNumber phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

}
