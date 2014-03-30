package fr.pinguet62.croquette.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import fr.pinguet62.croquette.action.sms.exchange.SendSMSAction;
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

    /** The selected {@link Conversation}. */
    private Conversation selectedConversation = null;

    /**
     * Default constructor. <br />
     * Connect user to {@code GTalk}.
     */
    public SmsManagedBean() {
	User.get().getXmppManager().connect();
    }

    /**
     * Destructor.<br />
     * Disconnect user of {@code GTalk}.
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
     * Gets the list of {@link Conversation}s of {@link User}.
     *
     * @return The list of {@link Contact}s.
     */
    public Iterable<Conversation> getConversations() {
	List<Conversation> conversations = new ArrayList<Conversation>(User
		.get().getConversations());
	Collections.sort(conversations);
	return conversations;
    }

    /**
     * Gets the input of selected {@link Conversation}.
     *
     * @return The input.
     */
    public String getInput() {
	if (selectedConversation == null)
	    return null;
	return selectedConversation.getInput();
    }

    /**
     * Gets the selected {@link Conversation}.
     *
     * @return The selected {@link Conversation}.
     */
    public Conversation getSelectedConversation() {
	if (selectedConversation != null)
	    selectedConversation.allRead();
	return selectedConversation;
    }

    /** Load old {@link Message}s of the selected {@link Conversation}. */
    public void loadOldMessages() {
	// TODO Delete this
	int nbMessages = (int) (5 * Math.random());
	for (int i = 0; i < nbMessages; i++) {
	    Message message = new Message();
	    message.setContent("SmsManagedBean.loadOldMessages()");
	    message.setConversation(selectedConversation);
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime(selectedConversation.first().getDate());
	    calendar.add(Calendar.DATE, -1);
	    message.setDate(calendar.getTime());
	    message.setRead(true);
	    message.setSent(((int) (2 * Math.random()) % 2) == 0);
	    message.setState(State.OK);
	    selectedConversation
	    .setHasOldMessages(((int) (2 * Math.random()) % 2) == 0);
	    selectedConversation.add(message);
	}
    }

    /**
     * Called when {@link User} want send the SMS to her {@link Contact}.<br />
     * Generate and execute the {@link SendSMSAction}.
     */
    public void send() {
	if (selectedConversation == null)
	    return;
	if ((getInput() == null) || getInput().isEmpty())
	    return;

	Message message = new Message();
	message.setContent(selectedConversation.getInput());
	message.setConversation(selectedConversation);
	message.setDate(new Date());
	message.setSent(true);

	SendSMSAction action = new SendSMSAction(message);
	action.execute();

	selectedConversation.add(message);
	selectedConversation.setInput(null);

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
	if (selectedConversation == null)
	    return;
	selectedConversation.setInput(input);
    }

    /**
     * Sets the selected {@link Conversation}.
     *
     * @param selectedConversation
     *            The selected {@link Conversation} to set.
     */
    public void setSelectedConversation(final Conversation selectedConversation) {
	this.selectedConversation = selectedConversation;
    }

}
