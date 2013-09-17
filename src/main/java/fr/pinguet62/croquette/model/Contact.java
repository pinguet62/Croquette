package fr.pinguet62.croquette.model;

/**
 * Contains informations about a contact.
 * 
 * @author Pinguet62
 */
public final class Contact {

    /** The first name. */
    private String firstName = null;

    /** The name. */
    private String name = null;

    /** The phone number. */
    private String phoneNumber = null;

    /**
     * Constructor.
     * 
     * @param name
     *            The name.
     */
    public Contact(final String name) {
	this.name = name;
    }

    /**
     * Gets the first name.
     * 
     * @return The first name.
     * @author Pinguet62
     */
    public String getFirstName() {
	return this.firstName;
    }

    /**
     * Gets the name.
     * 
     * @return The name.
     * @author Pinguet62
     */
    public String getName() {
	return this.name;
    }

    /**
     * Gets the phone number.
     * 
     * @return The phone number.
     * @author Pinguet62
     */
    public String getPhoneNumber() {
	return this.phoneNumber;
    }

    /**
     * Sets the first name.
     * 
     * @param firstName
     *            The first name to set.
     * @author Pinguet62
     */
    public void setFirstName(final String firstName) {
	this.firstName = firstName;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            The name to set.
     * @author Pinguet62
     */
    public void setName(final String name) {
	this.name = name;
    }

    /**
     * Sets the phone number.
     * 
     * @param phoneNumber
     *            The phone number to set.
     * @author Pinguet62
     */
    public void setPhoneNumber(final String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

}
