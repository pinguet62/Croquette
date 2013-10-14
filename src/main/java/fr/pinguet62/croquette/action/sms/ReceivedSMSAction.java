package fr.pinguet62.croquette.action.sms;

import java.text.DateFormat;

import javax.json.JsonObject;

import org.primefaces.push.PushContext;
import org.primefaces.push.PushContextFactory;

import fr.pinguet62.croquette.action.Action;
import fr.pinguet62.croquette.action.ActionException;
import fr.pinguet62.croquette.bean.SmsManagedBean;
import fr.pinguet62.croquette.model.Contact;
import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Message;
import fr.pinguet62.croquette.model.Message.State;
import fr.pinguet62.croquette.model.User;

/** SMS received. */
@Action(ReceivedSMSAction.ACTION_VALUE)
public final class ReceivedSMSAction extends SMSAction {

    /** The <code>action</code> value. */
    public static final String ACTION_VALUE = "SMS_RECEIVED";

    /** The JSON message. */
    private JsonObject jsonMessage = null;

    /**
     * Constructor.
     * 
     * @param jsonMessage
     *            The JSON message.
     */
    public ReceivedSMSAction(final JsonObject jsonMessage) {
	this.jsonMessage = jsonMessage;
    }

    /**
     * Create the {@link Message} from the JSON message. <br />
     * Add to {@link Conversation} and update the view.
     */
    @Override
    public void execute() {
	try {
	    Contact contact = User.get().getContact(
		    this.jsonMessage.getString(SMSAction.PHONE_NUMBER));

	    Message message = new Message();
	    message.setContact(contact);
	    message.setContent(this.jsonMessage.getString(SMSAction.CONTENT));
	    message.setDate(DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
		    DateFormat.DEFAULT).parse(
		    this.jsonMessage.getString(SMSAction.DATE)));
	    message.setRead(false);
	    message.setSent(false);
	    message.setState(State.OK);

	    contact.getConversation().add(message);
	} catch (Exception e) {
	    throw new ActionException(e);
	}

	PushContext pushContext = PushContextFactory.getDefault()
		.getPushContext();
	pushContext.push(SmsManagedBean.CHANNEL, null);
    }

}
