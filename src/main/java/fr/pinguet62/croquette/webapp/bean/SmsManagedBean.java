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

import org.jivesoftware.smack.XMPPConnection;

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

    private static final long serialVersionUID = 1;

    /** The selected {@link Conversation}. */
    private Conversation selectedConversation = null;

    /** Connect {@link User} to {@code GTalk}. */
    public SmsManagedBean() {
        User.get().getXmppManager().connect();
    }

    /** Disconnect user of {@link XMPPConnection}. */
    @Override
    public void finalize() throws Throwable {
        // TODO Implement
        super.finalize();
    }

    /**
     * Get the channel name for view updates.
     *
     * @return {@link PushResource#CHANNEL}
     */
    public String getChannel() {
        return PushResource.CHANNEL;
    }

    /**
     * Get {@link User}'s {@link Conversation}s.
     *
     * @return The {@link Conversation}s.
     */
    public Iterable<Conversation> getConversations() {
        List<Conversation> conversations = new ArrayList<Conversation>(User
                .get().getConversations());
        Collections.sort(conversations);
        return conversations;
    }

    /**
     * Get the input of current {@link Conversation}.
     *
     * @return The input.<br>
     *         {@code null} if no {@link Conversation}.
     */
    public String getInput() {
        return selectedConversation == null ? null : selectedConversation
                .getInput();
    }

    /**
     * Get the selected {@link Conversation}.
     * <p>
     * Set all {@link Message} as read, because {@link User} viewed the content.
     *
     * @return The selected {@link Conversation}.
     * @see Conversation#allRead()
     */
    public Conversation getSelectedConversation() {
        if (selectedConversation != null)
            selectedConversation.allRead();
        return selectedConversation;
    }

    /**
     * Load old {@link Conversation}s.
     *
     * @see LoadingConversationsAction
     */
    public void loadOldConversations() {
        Conversations conversations = User.get().getConversations();
        IAction action = new LoadingConversationsAction(
                conversations.isEmpty() ? null : conversations.last().getId());
        action.execute();
    }

    /**
     * Load old {@link Message}s of the selected {@link Conversation}.
     *
     * @see LoadingSMSAction
     */
    public void loadOldMessages() {
        IAction action = new LoadingSMSAction(selectedConversation);
        action.execute();
    }

    /**
     * Called when {@link User} want send the SMS to her {@link Contact}.
     *
     * @see SendSMSAction
     */
    public void send() {
        if (selectedConversation == null)
            return;
        if (getInput() == null || getInput().isEmpty())
            return;

        Message message = new Message();
        message.setConversation(selectedConversation);
        message.setDate(new Date());
        message.setSent(true);
        message.setContent(selectedConversation.getInput());

        SendSMSAction action = new SendSMSAction(message);
        action.execute();

        selectedConversation.add(message);
        selectedConversation.setInput(null);

        FacesContext.getCurrentInstance().addMessage(
                null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "SMS send success", message.getContent()));
    }

    /** @see Conversation#setInput(String) */
    public void setInput(String input) {
        if (selectedConversation == null)
            return;
        selectedConversation.setInput(input);
    }

    public void setSelectedConversation(Conversation selectedConversation) {
        this.selectedConversation = selectedConversation;
    }

}
