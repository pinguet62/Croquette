package fr.pinguet62.croquette.model;

import java.net.URL;
import java.util.List;
import java.util.TreeSet;

import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;

/** {@link Contacts} of {@link User}. */
public final class Contacts extends TreeSet<Contact> {

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

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
                for (com.google.gdata.data.extensions.PhoneNumber phoneNumber : contactEntry
                        .getPhoneNumbers()) {
                    if (!contactEntry.hasPhoneNumbers())
                        continue;
                    else if (!contactEntry.hasName())
                        continue;
                    else if (!contactEntry.getName().hasFullName())
                        continue;

                    Contact contact = new Contact();
                    contact.setName(contactEntry.getName().getFullName()
                            .getValue());
                    contact.setPhoneNumber(new PhoneNumber(phoneNumber
                            .getPhoneNumber()));
                    add(contact);
                }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Gets {@link Contact} by {@link PhoneNumber}.
     *
     * @param phoneNumber
     *            The {@link PhoneNumber}
     * @return The {@link Contact}, {@code null} if not find.
     */
    public Contact get(PhoneNumber phoneNumber) {
        if (phoneNumber == null)
            return null;
        for (Contact contact : this)
            if (phoneNumber.equals(contact.getPhoneNumber()))
                return contact;
        return null;
    }

}
