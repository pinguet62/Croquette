package fr.pinguet62.croquette.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import fr.pinguet62.croquette.model.Contact;

/**
 * Used to manage user's contact.
 * 
 * @author Pinguet62
 */
@ManagedBean(name = "contactsManagedBean")
@SessionScoped
public final class ContactsManagedBean implements Serializable {

    /** Auto generated UID. */
    private static final long serialVersionUID = -3264086471471697720L;

    /**
     * Gets the list of contacts of user.
     * 
     * @return Contacts
     * @author Pinguet62
     */
    public Iterable<Contact> getContacts() {
	// TODO Implement : ContactsManagedBean.getContacts()
	final Collection<Contact> contacts = new ArrayList<Contact>();
	for (int i = 1; i < 35; ++i)
	    contacts.add(new Contact("name " + i));
	return contacts;
    }

}
