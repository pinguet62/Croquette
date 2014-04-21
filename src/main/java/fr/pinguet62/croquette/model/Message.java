package fr.pinguet62.croquette.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Message exchanged with a {@link Contact}. */
// TODO Remove default constructor & setters + set final keyword attributes
public final class Message implements Comparable<Message>, Serializable {

    /** States of a {@link Message}. */
    public enum State {
	/** Error during sending */
	ERROR, /** Sending but not acknowledgment. */
	IN_PROGRESS, /** Normal status. */
	OK;
    }

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    /** The content. */
    private String content;

    // /** The {@link Contact}. */
    // private Contact contact = null;

    /** The {@link Conversation}. */
    private Conversation conversation;

    /** The date of issue or receiving. */
    private Date date;

    /** The id. */
    private Integer id; // TODO int

    /** If it was read. */
    private boolean read;

    /** If it was sent by user. */
    private Boolean sent;

    /** The {@link State}. */
    private State state = State.OK;

    /** Default constructor. */
    public Message() {
    }

    /**
     * Constructor.
     *
     * @param id
     *            The id.
     * @param date
     *            The date of issue or receiving.
     * @param content
     *            The content.
     */
    public Message(int id, Date date, String content) {
	this.id = id;
	this.date = date;
	this.content = content;
    }

    /**
     * Method used to compare the current {@link Message} to an other.<br />
     * Reverse chronological order.
     *
     * @param other
     *            The other {@link Message}.
     * @return A negative integer if this current {@link Message} is latest than
     *         the other, zero if there are equal, a positive integer otherwise.
     */
    @Override
    public int compareTo(Message other) {
	return date.compareTo(other.date);
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
	return content;
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
     * Gets the date of issue or receiving.
     *
     * @return The date.
     */
    public Date getDate() {
	return date;
    }

    /**
     * Gets the id.
     *
     * @return The id.
     */
    public Integer getId() {
	return id;
    }

    /**
     * Gets if it was sent by user.
     *
     * @return Result.
     */
    public Boolean getSent() {
	return sent;
    }

    /**
     * Gets the {@link State}.
     *
     * @return The {@link State}.
     */
    public State getState() {
	return state;
    }

    /**
     * Gets if it was read.
     *
     * @return Result.
     */
    public boolean isRead() {
	return read;
    }

    // /**
    // * Sets the {@link Contact}.
    // *
    // * @param contact
    // * The {@link Contact} to set.
    // */
    // public void setContact(Contact contact) {
    // this.contact = contact;
    // }

    /**
     * Gets if it was sent by user.
     *
     * @return Result.
     */
    public Boolean isSent() {
	return sent;
    }

    /**
     * Sets the content.
     *
     * @param content
     *            The content to set.
     */
    public void setContent(String content) {
	this.content = content;
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
     * Sets the date of issue or receiving.
     *
     * @param date
     *            The date to set.
     */
    public void setDate(Date date) {
	this.date = date;
    }

    /**
     * Sets the id.
     *
     * @param id
     *            The id.
     */
    public void setId(Integer id) {
	this.id = id;
    }

    /**
     * Sets if it was read.
     *
     * @param read
     *            If it was read.
     */
    public void setRead(boolean read) {
	this.read = read;
    }

    /**
     * Sets if it was sent by user.
     *
     * @param sent
     *            If sent by user.
     */
    public void setSent(Boolean sent) {
	this.sent = sent;
    }

    /**
     * Sets the {@link State}.
     *
     * @param state
     *            The {@link State}.
     */
    public void setState(State state) {
	this.state = state;
    }

    @Override
    public String toString() {
	return String.format("%d-%s-\"%s\"", id, new SimpleDateFormat(
		"dd/MM/yyyy").format(date), content);
    }

}
