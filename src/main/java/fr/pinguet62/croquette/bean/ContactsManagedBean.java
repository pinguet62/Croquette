package fr.pinguet62.croquette.bean;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

import fr.pinguet62.croquette.Session;
import fr.pinguet62.croquette.model.Contact;
import fr.pinguet62.croquette.model.Conversation;

/** Used to manage user's contact. */
@ManagedBean(name = "contactsManagedBean")
@SessionScoped
public final class ContactsManagedBean implements Serializable {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = -3264086471471697720L;

    /** The current {@link Conversation}. */
    private Conversation currentConversation = null;

    /** The selected {@link Contact}. */
    private Contact selectedContact = null;

    /**
     * Gets the list of contacts of user.
     * 
     * @return The list of contacts.
     */
    public ContactsDataModel getContacts() {
	return new ContactsDataModel(Session.getUser().getContacts());
    }

    /**
     * Gets the current {@link Conversation}.
     * 
     * @return The current {@link Conversation}.
     */
    public Conversation getCurrentConversation() {
	return this.currentConversation;
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
	Session.init();
    }

    /**
     * Call when user select a {@link Contact}.
     * 
     * @param event
     *            The selection event.
     */
    public void onContactSelected(final SelectEvent event) {
	this.selectedContact = (Contact) event.getObject();
	this.currentConversation = Session.getUser().getConversations()
		.get(this.selectedContact.getPhoneNumber());
    }

    /**
     * Call when user select a {@link Contact}.
     * 
     * @param event
     *            The unselection event.
     */
    public void onContactUnselected(final UnselectEvent event) {
	this.selectedContact = null;
	this.currentConversation = null;
    }

    /**
     * Sets the selected {@link Contact}.
     * 
     * @param selectedContact
     *            The selected {@link Contact} to set.
     */
    // public void setSelectedContact(final Contact selectedContact) {
    // this.selectedContact = selectedContact;
    // }

}
