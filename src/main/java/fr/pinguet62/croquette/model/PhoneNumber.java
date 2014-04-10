package fr.pinguet62.croquette.model;

import java.io.Serializable;

/** Wraper for phone number. */
public final class PhoneNumber implements Serializable {

    /** Serial version UID. */
    private static final long serialVersionUID = 1L;

    /** The value. */
    private final String value;

    /**
     * Constructor.
     *
     * @param value
     *            The value.
     */
    public PhoneNumber(String value) {
	this.value = value;
    }

    @Override
    public boolean equals(Object obj) {
	if (obj == null)
	    return false;
	if (this == obj)
	    return true;
	if (!(obj instanceof PhoneNumber))
	    return false;

	PhoneNumber other = (PhoneNumber) obj;
	return PhoneNumberUtils.compare(value, other.value);
    }

    /**
     * Gets the value.
     *
     * @return The value.
     */
    public String get() {
	return value;
    }

    /* Generate by Eclipse. */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = (prime * result) + ((value == null) ? 0 : value.hashCode());
	return result;
    }

    // TODO format phone number
    @Override
    public String toString() {
	return value;
    }

}
