package fr.pinguet62.croquette.model;

import java.io.Serializable;
import java.util.Date;

/** Message exchanged with a {@link Contact}. */
public final class Message implements Comparable<Message>, Serializable {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = 5126051315118508362L;

    /** The {@link Contact}. */
    private Contact contact = null;

    /** The content. */
    private String content = null;

    /** The date of issue or receiving. */
    private Date date = null;

    /** If it is in progress. */
    private Boolean inprogress = null;

    /** If if was read. */
    private boolean read = true;

    /** If it was sent by user. */
    private Boolean sent = null;

    /**
     * Method used to compare the current {@link Message} to an other.
     * 
     * @param other
     *            The other {@link Message}.
     * @return A negative integer if this current {@link Message} is oldest then
     *         the other, zero if there are equals, a positive integer if this
     *         current {@link Message} is newest then the other.
     */
    @Override
    public int compareTo(final Message other) {
	return this.date.compareTo(other.date);
    }

    /**
     * Gets the {@link Contact}.
     * 
     * @return The {@link Contact}.
     */
    public Contact getContact() {
	return this.contact;
    }

    /**
     * Gets the content.
     * 
     * @return The content.
     */
    public String getContent() {
	return this.content;
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
     * Gets if it is in progress.
     * 
     * @return Result.
     */
    public Boolean getInProgress() {
	return this.inprogress;
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
     * Gets if it was read.
     * 
     * @return Result.
     */
    public boolean isRead() {
	return this.read;
    }

    /**
     * Gets if it was sent by user.
     * 
     * @return Result.
     */
    public Boolean isSent() {
	return this.sent;
    }

    /**
     * Sets the {@link Contact}.
     * 
     * @param contact
     *            The {@link Contact} to set.
     */
    public void setContact(final Contact contact) {
	this.contact = contact;
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
     * Sets the date of issue or receiving.
     * 
     * @param date
     *            The date to set.
     */
    public void setDate(final Date date) {
	this.date = date;
    }

    /**
     * Sets if it is in progress.
     * 
     * @param inprogress
     *            Is in progress.
     */
    public void setInProgress(final Boolean inprogress) {
	this.inprogress = inprogress;
    }

    /**
     * Sets if it was read.
     * 
     * @param read
     *            the read to set
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

}
