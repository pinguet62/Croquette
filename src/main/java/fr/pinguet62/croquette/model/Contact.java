package fr.pinguet62.croquette.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.PhoneNumber;

/** Contains informations about a contact. */
public final class Contact implements Comparable<Contact>, Serializable {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = -594515732428430186L;

    /**
     * Convert Google contact to {@link Contact}s.
     * 
     * @param contactEntry
     *            Google contact.
     */
    public static Collection<Contact> convert(final ContactEntry contactEntry) {
	Collection<Contact> contacts = new ArrayList<Contact>();

	for (PhoneNumber phoneNumber : contactEntry.getPhoneNumbers()) {
	    if (!contactEntry.hasPhoneNumbers())
		continue;
	    else if (!contactEntry.hasName())
		continue;
	    else if (!contactEntry.getName().hasFullName())
		continue;

	    Contact contact = new Contact();
	    contact.setName(contactEntry.getName().getFullName().getValue());
	    contact.setPhoneNumber(phoneNumber.getPhoneNumber());
	}

	return contacts;
    }

    /** The {@link Conversation}. */
    private Conversation conversation = null;

    /** The name. */
    private String name = null;

    /** The phone number. */
    private String phoneNumber = null;

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
    public int compareTo(final Contact other) {
	int nameCompare = this.name.compareTo(other.name);
	if (nameCompare != 0)
	    return nameCompare;
	return this.phoneNumber.compareTo(other.phoneNumber);
    }

    /**
     * Gets the {@link Conversation}.
     * 
     * @return The {@link Conversation}.
     */
    public Conversation getConversation() {
	return this.conversation;
    }

    /**
     * Gets the name.
     * 
     * @return The name.
     */
    public String getName() {
	return this.name;
    }

    /**
     * Gets the phone number.
     * 
     * @return The phone number.
     */
    public String getPhoneNumber() {
	return this.phoneNumber;
    }

    /**
     * Sets the {@link Conversation}.
     * 
     * @param conversation
     *            The {@link Conversation} to set.
     */
    public void setConversation(final Conversation conversation) {
	this.conversation = conversation;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            The name to set.
     */
    public void setName(final String name) {
	this.name = name;
    }

    /**
     * Sets the phone number.
     * 
     * @param phoneNumber
     *            The phone number to set.
     */
    public void setPhoneNumber(final String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

}
