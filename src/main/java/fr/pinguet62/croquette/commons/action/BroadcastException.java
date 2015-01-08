package fr.pinguet62.croquette.commons.action;

/**
 * {@link Exception} thrown when the device receive the same message who it has
 * sent just before.
 */
public final class BroadcastException extends Exception {

    private static final long serialVersionUID = 1;

    public BroadcastException(String message) {
        super(message);
    }

}
