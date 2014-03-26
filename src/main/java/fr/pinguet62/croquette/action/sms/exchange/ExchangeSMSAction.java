package fr.pinguet62.croquette.action.sms.exchange;

import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.model.Contact;
import fr.pinguet62.croquette.model.Conversation;
import fr.pinguet62.croquette.model.Message;

/** Json keys for SMS exchange. */
public abstract class ExchangeSMSAction implements IAction {

    /** Key for the content of {@link Message}. */
    public static final String CONTENT = "content";

    /** Key for the id of {@link Conversation}. */
    public static final String CONVERSATION = "conversation";

    /** Key for the date of {@link Message} exchange. */
    public static final String DATE = "date";

    /** Key for the phone number of {@link Contact}. */
    public static final String PHONE_NUMBER = "phone number";

}
