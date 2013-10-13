package fr.pinguet62.croquette.bean;

import java.util.Calendar;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import fr.pinguet62.croquette.action.sms.SendSMSAction;
import fr.pinguet62.croquette.model.Contact;
import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.model.Message.State;
import fr.pinguet62.croquette.model.User;

/** Managed bean used to control the SMS view. */
@ManagedBean(name = "smsManagedBean")
@SessionScoped
public final class SmsManagedBean {

    /** The channel name of the connection with the view. */
    public static final String CHANNEL = "/channel";

    /** The selected {@link Contact}. */
    private Contact selectedContact = null;

    /**
     * Destructor. <br />
     * Disconnect user of <code>GTalk</code>.
     * 
     * @throws Throwable
     */
    @Override
    public void finalize() throws Throwable {
	// TODO Implement
	super.finalize();
    }

    /**
     * Gets the channel name of the connection with the view.
     * 
     * @return The channel.
     */
    public String getChannel() {
	return SmsManagedBean.CHANNEL;
    }

    /**
     * Gets the list of {@link Contact}s of {@link User}.
     * 
     * @return The list of {@link Contact}s.
     */
    public Iterable<Contact> getContacts() {
	return User.get().getContacts();
    }

    /**
     * Gets the current {@link Conversation} with the selected {@link Contact}.
     * 
     * @return The current {@link Conversation}.
     */
    public Conversation getCurrentConversation() {
	if (this.selectedContact == null)
	    return null;
	this.selectedContact.getConversation().allRead();
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

    /**
     * Initialization of this bean. <br />
     * Connect user to <code>GTalk</code>.
     */
    @PostConstruct
    private void init() {
	User.initTest();

	// TODO Implement
    }

    /** Load old {@link Message}s of the current {@link Conversation}. */
    public void loadOldMessages() {
	// TODO Delete this
	int nbMessages = (int) (5 * Math.random());
	for (int i = 0; i < nbMessages; i++) {
	    Message message = new Message();
	    message.setContact(this.selectedContact);
	    message.setContent("SmsManagedBean.loadOldMessages()");
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(this.getCurrentConversation().first().getDate());
	    calendar.add(Calendar.DATE, -1);
	    message.setDate(calendar.getTime());
	    message.setRead(true);
	    message.setSent(((int) (2 * Math.random()) % 2) == 0);
	    message.setState(State.OK);
	    this.getCurrentConversation().add(message);
	}
    }

    /**
     * Called when {@link User} want send the SMS to her {@link Contact}. <br />
     * Generate and execute the {@link SendSMSAction}.
     */
    public void send() {
	if (this.getCurrentConversation() == null)
	    return;
	if ((this.getInput() == null) || this.getInput().isEmpty())
	    return;

	Message message = new Message();
	message.setContact(this.selectedContact);
	message.setContent(this.getCurrentConversation().getInput());
	message.setDate(new Date());
	message.setSent(true);

	SendSMSAction action = new SendSMSAction(message);
	action.execute();

	this.getCurrentConversation().add(message);
	this.getCurrentConversation().setInput(null);

	FacesContext.getCurrentInstance().addMessage(
		null,
		new FacesMessage(FacesMessage.SEVERITY_INFO,
			"SMS send success", message.getContent()));
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
