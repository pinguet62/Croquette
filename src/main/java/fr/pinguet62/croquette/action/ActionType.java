package fr.pinguet62.croquette.action;

/**
 * Defines possible actions in application. <br />
 * Values are used into JSON message exchanged with smartphone.
 */
public enum ActionType {

    SMS_RECEIVED("SMS_RECEIVED"), SMS_SEND("SMS_SEND"), SMS_SENT_CONFIRM(
	    "SMS_SENT_CONFIRM");

    /**
     * Get {@link ActionType} from value.
     * 
     * @param value
     *            The value.
     */
    public static ActionType fromValue(final String value) {
	if (value == null)
	    return null;
	for (final ActionType actionType : ActionType.values())
	    if (actionType.getValue().equals(value))
		return actionType;
	return null;
    }

    /** The value. */
    private String value = null;

    /**
     * Constructor.
     * 
     * @param value
     *            The value.
     */
    private ActionType(final String value) {
	this.value = value;
    }

    /**
     * Get value.
     * 
     * @return The value.
     */
    public String getValue() {
	return this.value;
    }

}
