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

    /** The phone number. */
    private String phoneNumber;

    /**
     * Method used to sort {@link Contact}s by <code>name</code>.<br />
     * Sort by <code>phone number</code> if they are the same <code>name</code>.
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
	return phoneNumber.compareTo(other.phoneNumber);
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
     * Gets the phone number.
     *
     * @return The phone number.
     */
    public String getPhoneNumber() {
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
     * Sets the phone number.
     *
     * @param phoneNumber
     *            The phone number to set.
     */
    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

}
