package fr.pinguet62.croquette.model;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;

public final class Contacts extends TreeSet<Contact> {

    /** Auto generated serial version UID. */
    private static final long serialVersionUID = 3769558875221823124L;

    /**
     * Download {@link ContactEntry} from Google account with the OAuth token. <br />
     * Pop list of {@link Contact}s of the {@link User}.
     */
    public void downloadGoogle() {
	try {
	    ContactsService contactsService = new ContactsService("Croquette");
	    contactsService.setHeader("Authorization", "Bearer "
		    + User.get().getToken());
	    URL feedUrl = new URL(
		    "https://www.google.com/m8/feeds/contacts/default/full");
	    ContactFeed resultFeed = contactsService.getFeed(feedUrl,
		    ContactFeed.class);
	    List<ContactEntry> contactEntries = resultFeed.getEntries();
	    for (ContactEntry contactEntry : contactEntries) {
		Collection<Contact> contacts = Contact.convert(contactEntry);
		this.addAll(contacts);
	    }
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }

}
