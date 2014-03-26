package fr.pinguet62.croquette.model;

import java.net.URL;
import java.util.List;
import java.util.TreeSet;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.extensions.PhoneNumber;

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
	    for (ContactEntry contactEntry : contactEntries)
		for (PhoneNumber phoneNumber : contactEntry.getPhoneNumbers()) {
		    if (!contactEntry.hasPhoneNumbers())
			continue;
		    else if (!contactEntry.hasName())
			continue;
		    else if (!contactEntry.getName().hasFullName())
			continue;

		    Contact contact = new Contact();
		    contact.setName(contactEntry.getName().getFullName()
			    .getValue());
		    contact.setPhoneNumber(phoneNumber.getPhoneNumber());
		    add(contact);
		}
	} catch (Exception exception) {
	    exception.printStackTrace();
	}
    }

}
