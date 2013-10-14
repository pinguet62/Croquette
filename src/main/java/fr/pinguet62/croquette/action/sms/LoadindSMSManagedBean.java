package fr.pinguet62.croquette.action.sms;

import java.text.DateFormat;

import javax.json.Json;
import javax.json.JsonObject;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.model.Conversation;

/** Load old SMS of a {@link Conversation}. */
@Action(LoadindSMSManagedBean.ACTION_VALUE)
public final class LoadindSMSManagedBean extends SMSAction {

    /** The <code>action</code> value. */
    public static final String ACTION_VALUE = "SMS_LOADING";

    /** Key for number of SMS to load. */
    public static final String COUNT_KEY = "count";

    /** Value for number of SMS to load. */
    public static final int COUNT_VALUE = 5;

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
     * Create the {@link JsonObject}.<br />
     * Send to GTalk account.
     */
    @Override
    public void execute() {
	this.conversation.getContact();
	Json.createObjectBuilder()
		.add(IAction.ACTION_KEY, LoadindSMSManagedBean.ACTION_VALUE)
		.add(LoadindSMSManagedBean.COUNT_KEY,
			LoadindSMSManagedBean.COUNT_VALUE)
		.add(LoadindSMSManagedBean.OLDEST,
			DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
				DateFormat.DEFAULT).format(
				this.conversation.first().getDate()))
		.add(SMSAction.PHONE_NUMBER,
			this.conversation.getContact().getPhoneNumber())
		.build();
    }

}
