package fr.pinguet62.croquette.model;

import java.io.Serializable;
import java.util.Date;

/** Message exchanged with a {@link Contact}. */
public final class Message implements Comparable<Message>, Serializable {

    public enum State {
	ERROR, IN_PROGRESS, OK;
    }

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = 5126051315118508362L;

    // /** The {@link Contact}. */
    // private Contact contact = null;

    /** The content. */
    private String content = null;

    /** The {@link Conversation}. */
    private Conversation conversation = null;

    /** The date of issue or receiving. */
    private Date date = null;

    /** The id. */
    private Integer id = null;

    /** If it was read. */
    private boolean read = true;

    /** If it was sent by user. */
    private Boolean sent = null;

    /** The {@link State}. */
    private State state = null;

    /**
     * Method used to compare the current {@link Message} to an other.
     * 
     * @param other
     *            The other {@link Message}.
     * @return A negative integer if this current {@link Message} is oldest than
     *         the other, zero if there are equal, a positive integer otherwise.
     */
    @Override
    public int compareTo(final Message other) {
	return this.date.compareTo(other.date);
    }

    // /**
    // * Gets the {@link Contact}.
    // *
    // * @return The {@link Contact}.
    // */
    // public Contact getContact() {
    // return this.contact;
    // }

    /**
     * Gets the content.
     * 
     * @return The content.
     */
    public String getContent() {
	return this.content;
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
     * Gets the date of issue or receiving.
     * 
     * @return The date.
     */
    public Date getDate() {
	return this.date;
    }

    /**
     * Gets the id.
     * 
     * @return The id.
     */
    public Integer getId() {
	return this.id;
    }

    /**
     * Gets if it was sent by user.
     * 
     * @return Result.
     */
    public Boolean getSent() {
	return this.sent;
    }

    /**
     * Gets the {@link State}.
     * 
     * @return The {@link State}.
     */
    public State getState() {
	return this.state;
    }

    /**
     * Gets if it was read.
     * 
     * @return Result.
     */
    public boolean isRead() {
	return this.read;
    }

    // /**
    // * Sets the {@link Contact}.
    // *
    // * @param contact
    // * The {@link Contact} to set.
    // */
    // public void setContact(final Contact contact) {
    // this.contact = contact;
    // }

    /**
     * Gets if it was sent by user.
     * 
     * @return Result.
     */
    public Boolean isSent() {
	return this.sent;
    }

    /**
     * Sets the content.
     * 
     * @param content
     *            The content to set.
     */
    public void setContent(final String content) {
	this.content = content;
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
     * Sets the date of issue or receiving.
     * 
     * @param date
     *            The date to set.
     */
    public void setDate(final Date date) {
	this.date = date;
    }

    /**
     * Sets the id.
     * 
     * @param id
     *            The id.
     */
    public void setId(final Integer id) {
	this.id = id;
    }

    /**
     * Sets if it was read.
     * 
     * @param read
     *            If it was read.
     */
    public void setRead(final boolean read) {
	this.read = read;
    }

    /**
     * Sets if it was sent by user.
     * 
     * @param sent
     *            If sent by user.
     */
    public void setSent(final Boolean sent) {
	this.sent = sent;
    }

    /**
     * Sets the {@link State}.
     * 
     * @param state
     *            The {@link State}.
     */
    public void setState(final State state) {
	this.state = state;
    }

}
