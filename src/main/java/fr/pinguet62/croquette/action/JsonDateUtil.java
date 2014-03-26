package fr.pinguet62.croquette.action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Utils for format {@link Date}s on Json messages. */
public final class JsonDateUtil {

    /** The ISO 8601 formatter. */
    private static final DateFormat FORMATTER = new SimpleDateFormat(
	    "YYYY-MM-DDThh:mm:ss");

    /**
     * Convert formatted date to {@link Date}.
     *
     * @param formatted
     *            The formatted {@link Date}.
     * @return The {@link Date}.
     * @throws ParseException
     *             If the {@link String} can't be parsed.
     */
    public static Date fromString(String formatted) throws ParseException {
	return FORMATTER.parse(formatted);
    }

    public static String toString(Date date) {
	return FORMATTER.format(date);
    }

    /** Default constructor. */
    private JsonDateUtil() {
    }

}
