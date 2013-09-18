package fr.pinguet62.croquette.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.pinguet62.croquette.Session;
import fr.pinguet62.croquette.model.Contact;

/** Used to manage user's contact. */
@ManagedBean(name = "contactsManagedBean")
@SessionScoped
public final class ContactsManagedBean implements Serializable {

    /** Auto generated UID. */
    private static final long serialVersionUID = -3264086471471697720L;

    /**
     * Gets the list of contacts of user.
     * 
     * @return The list of contacts.
     */
    public Iterable<Contact> getContacts() {
	return Session.getUser().getContacts();
    }

}
