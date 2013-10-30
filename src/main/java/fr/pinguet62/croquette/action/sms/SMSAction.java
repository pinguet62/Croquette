package fr.pinguet62.croquette.action.sms;

import fr.pinguet62.croquette.action.IAction;
import fr.pinguet62.croquette.model.Conversation;

/** Abstract class to define common methods and keys for JSON messages. */
public abstract class SMSAction implements IAction {

    /** Key for content. */
    public static final String CONTENT = "content";

    /** Key for {@link Conversation} id. */
    public static final String CONVERSATION = "conversation";

    /** Key for date. */
    public static final String DATE = "date";

    /** Key for phone number. */
    public static final String PHONE_NUMBER = "phone number";

}
