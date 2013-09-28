package fr.pinguet62.croquette.bean;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;

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

    /** Initialization of this bean. */
    @PostConstruct
    private void init() {
	Session.initTest();
    }

    /**
     * Called when user enter text.
     * 
     * @param event
     *            The event.
     */
    public void inputChange(final AjaxBehaviorEvent event) {
	if (this.getCurrentConversation() == null)
	    return;
	this.getCurrentConversation().setInput("");
    }

    /**
     * Call when user select a {@link Contact}.
     * 
     * @param event
     *            The selection event.
     */
    public void onContactSelected(final SelectEvent event) {
	this.selectedContact = (Contact) event.getObject();
	// this.currentConversation = Session.getUser().getConversations()
	// .get(this.selectedContact.getPhoneNumber());
    }

    /**
     * Call when user select a {@link Contact}.
     * 
     * @param event
     *            The unselection event.
     */
    public void onContactUnselected(final UnselectEvent event) {
	this.selectedContact = null;
	// this.currentConversation = null;
    }

}
