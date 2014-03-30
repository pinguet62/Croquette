package fr.pinguet62.croquette.action;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation used to define the value of {@code action} key in the JSON
 * message. <br />
 * The annotated classes are explored by {@link ActionFactory} to determinate
 * the action to perform.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    /** Value of {@code action} key. */
    String value();

}
