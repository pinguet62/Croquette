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
	 * Getter first name.
	 * 
	 * @return The first name.
	 * @author Pinguet62
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * Getter name.
	 * 
	 * @return The name
	 * @author Pinguet62
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter first name.
	 * 
	 * @param firstName
	 *            The first name.
	 * @author Pinguet62
	 */
	public void setFirstName(final String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Setter name.
	 * 
	 * @param name
	 *            The name.
	 * @author Pinguet62
	 */
	public void setName(final String name) {
		this.name = name;
	}

}
