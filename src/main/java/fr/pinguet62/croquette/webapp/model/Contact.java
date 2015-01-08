package fr.pinguet62.croquette.webapp.model;

import java.io.Serializable;

/** Contains informations about a contact. */
public final class Contact implements Comparable<Contact>, Serializable {

    private static final long serialVersionUID = 1;

    private Conversation conversation;

    private String name;

    private PhoneNumber phoneNumber;

    /**
     * Method used to sort {@link Contact}s by {@link #name}.<br>
     * If they have the same {@link #name}, then sort by {@link #phoneNumber}.
     */
    @Override
    public int compareTo(Contact other) {
        int nameCompare = name.compareTo(other.name);
        if (nameCompare != 0)
            return nameCompare;
        return phoneNumber.get().compareTo(other.phoneNumber.get());
    }

    public Conversation getConversation() {
        return conversation;
    }

    public String getName() {
        return name;
    }

    public PhoneNumber getPhoneNumber() {
        return phoneNumber;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return name + " (" + phoneNumber + ")";
    }

}
