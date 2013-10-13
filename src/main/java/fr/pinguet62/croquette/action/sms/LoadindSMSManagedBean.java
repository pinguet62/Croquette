package fr.pinguet62.croquette.action.sms;

import java.text.DateFormat;

import javax.json.Json;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.model.Conversation;

/** Load old SMS. */
@Action(LoadindSMSManagedBean.ACTION_KEY)
public final class LoadindSMSManagedBean extends SMSAction {

    /** The <code>action</code> value. */
    public static final String ACTION_KEY = "SMS_LOADOLD";

    /**
     * Key for the date of the oldest {@link Message} of the
     * {@link Conversation} .
     */
    public static final String OLDEST = "oldest";

    /**
     * The {@link Conversation} that the {@link User} wants to load older
     * {@link Message}s.
     */
    private final Conversation conversation;

    /**
     * Constructor.
     * 
     * @param conversation
     *            The {@link Conversation} that the {@link User} wants to load
     *            older {@link Message}s.
     */
    public LoadindSMSManagedBean(final Conversation conversation) {
	this.conversation = conversation;
    }

    /**
     * Create the JsonObject from the Message.<br />
     * Send to GTalk account.
     */
    @Override
    public void execute() {
	this.conversation.getContact();
	Json.createObjectBuilder()
		.add(IAction.ACTION_KEY, LoadindSMSManagedBean.ACTION_KEY)
		.add(SMSAction.PHONE_NUMBER,
			this.conversation.getContact().getPhoneNumber())
		.add(LoadindSMSManagedBean.OLDEST,
			DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
				DateFormat.DEFAULT).format(
				this.conversation.first().getDate())).build();
    }
}
