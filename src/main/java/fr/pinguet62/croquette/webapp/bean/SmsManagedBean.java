package fr.pinguet62.croquette.webapp.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import fr.pinguet62.croquette.webapp.action.IAction;
import fr.pinguet62.croquette.webapp.action.sms.conversation.LoadingSMSAction;
import fr.pinguet62.croquette.webapp.action.sms.conversations.LoadingConversationsAction;
import fr.pinguet62.croquette.webapp.action.sms.exchange.SendSMSAction;
import fr.pinguet62.croquette.webapp.model.Contact;
import fr.pinguet62.croquette.webapp.model.Conversation;
import fr.pinguet62.croquette.webapp.model.Conversations;
import fr.pinguet62.croquette.webapp.model.Message;
import fr.pinguet62.croquette.webapp.model.User;

/** Managed bean used to control the SMS view. */
@ManagedBean(name = "smsManagedBean")
@SessionScoped
public final class SmsManagedBean implements Serializable {

    /** The channel name of the connection with the view. */
    public static final String CHANNEL = "/channel";

    private static final long serialVersionUID = 1;

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

    /** Load old {@link Conversation}s. */
    public void loadOldConversations() {
        Conversations conversations = User.get().getConversations();
        IAction action = new LoadingConversationsAction(
                conversations.isEmpty() ? null : conversations.last().getId());
        action.execute();
    }

    /** Load old {@link Message}s of the selected {@link Conversation}. */
    public void loadOldMessages() {
        new LoadingSMSAction(selectedConversation).execute();
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
