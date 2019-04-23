package fr.pinguet62.croquette.webapp.action;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import fr.pinguet62.croquette.commons.action.IAction;

/**
 * Annotation used to define {@link IAction} to execute when the message is
 * received.
 * <p>
 * The annotation must be intended to {@link IAction} to execute after reception
 * of message from other device.
 * <p>
 * The annotation class must respect this specification:
 * <ul>
 * <li>Implement the {@link IAction} interface;</li>
 * <li>Define a constructor with simple {@link String} parameter. It's the JSON
 * message received from Smartphone who will passed to the constructor.</li>
 * </ul>
 *
 * @see ActionFactory
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SmartphoneHandler {

    /** The key of {@link IAction}. */
    String value();

}
