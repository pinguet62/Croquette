package fr.pinguet62.croquette.model;

/**
 * Conversation with a contact.
 * 
 * @author Pinguet62
 */
public final class Conversation {

	/** The message. */
	private String message = null;

	/**
	 * Getter message.
	 * 
	 * @return The message.
	 * @author Pinguet62
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Setter message.
	 * 
	 * @param message
	 *            The message.
	 * @author Pinguet62
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

}
