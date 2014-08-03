package fr.pinguet62.croquette.test;

import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

    /**
     * Generate date from {@link Calendar}.
     *
     * @param year
     *            The year.
     * @param month
     *            The month (0 for January).
     * @param date
     *            The day in month.
     * @return The {@link Date}.
     */
    public static Date generateDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(year, month, day);
        return calendar.getTime();
    }

    /** Private constructor. */
    private DateUtil() {
    }

}
