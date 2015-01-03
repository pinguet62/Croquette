package fr.pinguet62.croquette.webapp.action;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to define the handler for actions who come from the
 * Smartphone.<br>
 * WARNING: only action from Smartphone must be annotated with this annotation!
 * If an {@link IAction} executed by web application is annotated with this, the
 * application will react to the broadcast message and an
 * {@link UnsupportedOperationException} will be thrown.
 * <p>
 * The annotation class must respect this specification:
 * <ul>
 * <li>Implement the {@link IAction} interface;</li>
 * <li>Define a constructor with simple {@link String} parameter. It's the JSON
 * message received from Smartphone who will passed to the constructor.</li>
 * </ul>
 * <p>
 * The key value must be unique, otherwise an handler will be replaced by
 * another.
 *
 * @see ActionFactory
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SmartphoneHandler {

    /** The key of {@link IAction}. */
    String value();

}
