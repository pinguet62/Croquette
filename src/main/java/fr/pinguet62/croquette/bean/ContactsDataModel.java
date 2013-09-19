package fr.pinguet62.croquette.bean;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import fr.pinguet62.croquette.model.Contact;

/** Data model used to display the list of {@link Contact}s. */
public final class ContactsDataModel extends ListDataModel<Contact> implements
	SelectableDataModel<Contact> {

    /**
     * Constructor with the list of {@link Contact}s.
     * 
     * @param contacts
     *            The {@link Contact}s.
     */
    public ContactsDataModel(final List<Contact> contacts) {
	super(contacts);
    }

    /**
     * Gets {@link Contact} from the row key.
     * 
     * @param rowKey
     *            The row key.
     * @return The {@link Contact}.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Contact getRowData(final String rowKey) {
	if (rowKey == null)
	    return null;
	for (final Contact contact : (List<Contact>) this.getWrappedData())
	    if (rowKey.equals(contact.getPhoneNumber()))
		return contact;
	return null;
    }

    /**
     * Gets the row key from the {@link Contact}.
     * 
     * @param contact
     *            The {@link Contact}.
     * @return The row key.
     */
    @Override
    public Object getRowKey(final Contact contact) {
	return contact.getPhoneNumber();
    }

}
