package fr.pinguet62.croquette.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.extensions.PhoneNumber;

/** Contains informations about a contact. */
public final class Contact implements Serializable {

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
	    else if (!contactEntry.getName().hasFamilyName())
		continue;
	    else if (!contactEntry.getName().hasGivenName())
		continue;

	    Contact contact = new Contact();
	    contact.setName(contactEntry.getName().getFamilyName().getValue());
	    contact.setFirstName(contactEntry.getName().getGivenName()
		    .getValue());
	    contact.setPhoneNumber(phoneNumber.getPhoneNumber());
	}

	return contacts;
    }

    /** The {@link Conversation}. */
    private Conversation conversation = new Conversation();

    /** The first name. */
    private String firstName = null;

    /** The name. */
    private String name = null;

    /** The phone number. */
    private String phoneNumber = null;

    /**
     * Gets the {@link Conversation}.
     * 
     * @return The {@link Conversation}.
     */
    public Conversation getConversation() {
	return this.conversation;
    }

    /**
     * Gets the first name.
     * 
     * @return The first name.
     */
    public String getFirstName() {
	return this.firstName;
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
     * Sets the first name.
     * 
     * @param firstName
     *            The first name to set.
     */
    public void setFirstName(final String firstName) {
	this.firstName = firstName;
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
