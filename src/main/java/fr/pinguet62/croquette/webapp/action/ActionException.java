package fr.pinguet62.croquette.webapp.action;

/** Exception occurring during {@link IAction} execution. */
public final class ActionException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public ActionException(Throwable cause) {
        super(cause);
    }

}
