package fr.pinguet62.croquette.webapp.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Message exchanged with a {@link Contact}. */
public final class Message implements Serializable, Comparable<Message> {

    /** State of a {@link Message}. */
    public enum State {
        /** Error during sending. */
        ERROR, /** Sending but not acknowledgment. */
        IN_PROGRESS, /** Normal status. */
        OK;
    }

    private static final long serialVersionUID = 1;

    private String content;

    // TODO
    // /** The {@link Contact}. */
    // private Contact contact = null;

    private Conversation conversation;

    /** The date of sending or reception. */
    private Date date;

    private Integer id;

    private boolean read;

    private Boolean sent;

    private State state = State.OK;

    public Message() {
    }

    public Message(int id, Date date, String content) {
        this.id = id;
        this.date = date;
        this.content = content;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Reverse chronological order.
     */
    @Override
    public int compareTo(Message other) {
        return date.compareTo(other.date);
    }

    // /**
    // * Get the {@link Contact}.
    // *
    // * @return The {@link Contact}.
    // */
    // public Contact getContact() {
    // return this.contact;
    // }

    public String getContent() {
        return content;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public Date getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }

    public Boolean getSent() {
        return sent;
    }

    public State getState() {
        return state;
    }

    public boolean isRead() {
        return read;
    }

    // /**
    // * Set the {@link Contact}.
    // *
    // * @param contact
    // * The {@link Contact} to set.
    // */
    // public void setContact(Contact contact) {
    // this.contact = contact;
    // }

    public Boolean isSent() {
        return sent;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public void setSent(Boolean sent) {
        this.sent = sent;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return String.format("%d-%s-\"%s\"", id, new SimpleDateFormat(
                "dd/MM/yyyy").format(date), content);
    }

}
