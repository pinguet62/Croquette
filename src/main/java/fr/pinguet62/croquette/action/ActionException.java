package fr.pinguet62.croquette.action;

/** Exception occurring during actions. */
public class ActionException extends RuntimeException {

    /** Serial version UID. */
    private static final long serialVersionUID = 1;

    /**
     * Constructor with initial Exception.
     *
     * @param cause
     *            The cause.
     */
    public ActionException(Throwable cause) {
        super(cause);
    }

}
