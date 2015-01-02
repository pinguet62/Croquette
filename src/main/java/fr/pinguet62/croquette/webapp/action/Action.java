package fr.pinguet62.croquette.webapp.action;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to define the unique key of {@link IAction}. These annotated
 * classes are scanned at program lunch to identify {@link IAction}.
 * <p>
 * The implements of {@link IAction} must have a constructor with {@link String}
 * parameter.
 * <p>
 * Only action from Smartphone must be annotated with this annotation! These
 * {@link IAction} will be executed automatically after reception of JSON
 * messages.
 *
 * @see ActionFactory
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    /** The key of {@link IAction}. */
    String value();

}
