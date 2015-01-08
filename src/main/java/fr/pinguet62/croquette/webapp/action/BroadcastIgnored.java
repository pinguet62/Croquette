package fr.pinguet62.croquette.webapp.action;

import fr.pinguet62.croquette.commons.action.IAction;

/**
 * Annotation used to define {@link IAction} not to execute when the message is
 * received.
 * <p>
 * The annotation must be intended to {@link IAction} who sends a message to
 * other device, and to ignore the same message receive.
 *
 * @see ActionFactory
 */
public @interface BroadcastIgnored {

    /** The key of {@link IAction}. */
    String value();

}
