package fr.pinguet62.croquette.model;

import java.util.Date;

/**
 * Message exchanged with a {@link Contact}.
 * 
 * @author Pinguet62
 */
public final class Message {

    /** The content. */
    private String content = null;

    /** The date of issue or receiving. */
    private Date date = null;

    /** If it is in progress. */
    private Boolean inprogress = null;

    /** If it was sent by user. */
    private Boolean sent = null;

    /**
     * Gets the content.
     * 
     * @return The content.
     * @author Pinguet62
     */
    public String getContent() {
	return this.content;
    }

    /**
     * Gets the date of issue or receiving.
     * 
     * @return The date.
     * @author Pinguet62
     */
    public Date getDate() {
	return this.date;
    }

    /**
     * Gets if it is in progress.
     * 
     * @return Result.
     * @author Pinguet62
     */
    public Boolean getInProgress() {
	return this.inprogress;
    }

    /**
     * Gets if it was sent by user.
     * 
     * @return Result.
     * @author Pinguet62
     */
    public Boolean isSent() {
	return this.sent;
    }

    /**
     * Sets the content.
     * 
     * @param content
     *            The content to set.
     * @author Pinguet62
     */
    public void setContent(final String content) {
	this.content = content;
    }

    /**
     * Sets the date of issue or receiving.
     * 
     * @param date
     *            The date to set.
     * @author Pinguet62
     */
    public void setDate(final Date date) {
	this.date = date;
    }

    /**
     * Sets if it is in progress.
     * 
     * @param inprogress
     *            Is in progress.
     * @author Pinguet62
     */
    public void setInProgress(final Boolean inprogress) {
	this.inprogress = inprogress;
    }

    /**
     * Sets if it was sent by user.
     * 
     * @param sent
     *            If sent by user.
     * @author Pinguet62
     */
    public void setSent(final Boolean sent) {
	this.sent = sent;
    }

}
