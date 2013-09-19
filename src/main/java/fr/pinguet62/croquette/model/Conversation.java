package fr.pinguet62.croquette.model;

import java.util.HashSet;

/**
 * Contains list of {@link Message}s.
 * <p>
 * Extends {@link HashSet} to add some methods.
 */
public final class Conversation extends HashSet<Message> {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = -6466753476994997663L;

    /** The sender or addressee {@link Contact}. */
    private Contact contact = null;

    /**
     * The input text of the new {@link Message} into this {@link Conversation}.
     */
    private String input = null;

    /**
     * Gets the sender or addressee {@link Contact}.
     * 
     * @return The {@link Contact}.
     */
    public Contact getContact() {
	return this.contact;
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
     * Sets the sender or addressee {@link Contact}.
     * 
     * @param contact
     *            The {@link Contact}.
     */
    public void setContact(final Contact contact) {
	this.contact = contact;
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
