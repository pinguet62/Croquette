package fr.pinguet62.croquette.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.pinguet62.croquette.Session;
import fr.pinguet62.croquette.model.Contact;
import fr.pinguet62.croquette.model.Conversation;

@ManagedBean(name = "smsManagedBean")
@SessionScoped
public final class SmsManagedBean {

    /** The selected {@link Contact}. */
    private Contact selectedContact = null;

    /**
     * Gets the list of {@link Contact}s of {@link User}.
     * 
     * @return The list of {@link Contact}s.
     */
    public Iterable<Contact> getContacts() {
	return Session.getUser().getContacts();
    }

    /**
     * Gets the current {@link Conversation} with the selected {@link Contact}.
     * 
     * @return The current {@link Conversation}.
     */
    public Conversation getCurrentConversation() {
	if (this.selectedContact == null)
	    return null;
	return this.selectedContact.getConversation();
    }

    /**
     * Gets the input.
     * 
     * @return The input.
     */
    public String getInput() {
	if (this.getCurrentConversation() == null)
	    return null;
	return this.getCurrentConversation().getInput();
    }

    /**
     * Gets the selected {@link Contact}.
     * 
     * @return The selected {@link Contact}.
     */
    public Contact getSelectedContact() {
	return this.selectedContact;
    }

    /** Initialization of this bean. */
    @PostConstruct
    private void init() {
	Session.initTest();
    }

    /**
     * Sets the input.
     * 
     * @param input
     *            The input to set.
     */
    public void setInput(final String input) {
	if (this.getCurrentConversation() == null)
	    return;
	this.getCurrentConversation().setInput(input);
    }

    /**
     * Sets the selected {@link Contact}.
     * 
     * @param selectedContact
     *            The selected {@link Contact} to set.
     */
    public void setSelectedContact(final Contact selectedContact) {
	this.selectedContact = selectedContact;
    }

}
