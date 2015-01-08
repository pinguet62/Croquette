package fr.pinguet62.croquette.android.sms.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import fr.pinguet62.croquette.android.MainActivity;

/** Used to select data from database. */
public final class SmsService {

    private static final Uri DB_SMS = Uri.parse("content://sms/all");

    private static final Uri DB_THREADS = Uri
            .parse("content://sms/conversations");

    /** @return The {@link Sms}, {@code null} if not found. */
    public static Sms getByAddressAndBody(String address, String body) {
        Cursor cursor = getContentResolver().query(DB_SMS, null,
                "address=? and body=?", new String[] { address, body }, null);
        if (!cursor.moveToFirst())
            return null;
        return new Sms(cursor);
    }

    /**
     * Get the {@link Sms}s of a {@link Thread}.
     * <p>
     * Results are selected as a pagination.<br>
     * Example: for {@code offset=2} and {@code limit=5}, this method will
     * return the values with index {@code 2} to {@code 6}.
     *
     * @return The {@link Sms}, sorted by descending order, from latest to
     *         oldest.<br>
     *         An empty {@link List} if no result found.
     */
    public static List<Sms> getByThread(int threadId, Integer limit,
            Integer offset) {
        if (offset == null)
            offset = 0;

        Cursor cursor = getContentResolver().query(DB_SMS, null, "thread_id=?",
                new String[] { Integer.toString(threadId) }, "_id desc");
        // Paginate
        List<Sms> smss = new ArrayList<Sms>(limit);
        for (int i = offset; i < offset + limit && cursor.moveToPosition(i); i++) {
            Sms sms = new Sms(cursor);
            smss.add(sms);
        }
        return smss;
    }

    private static ContentResolver getContentResolver() {
        return MainActivity.CONTEXT.getContentResolver();
    }

    public static List<Thread> getThreads(Integer limit, Integer offset) {
        if (offset == null)
            offset = 0;

        Cursor cursor = getContentResolver().query(DB_THREADS, null, null,
                null, "_id desc");
        // Paginate
        List<Thread> threads = new ArrayList<Thread>(limit);
        for (int i = offset; i < offset + limit && cursor.moveToPosition(i); i++) {
            Thread thread = new Thread(cursor);
            threads.add(thread);
            // Load SMSs
            Cursor cursor2 = getContentResolver().query(DB_SMS, null,
                    "thread_id=?",
                    new String[] { Integer.toString(thread.getId()) }, null);
            Sms sms = new Sms(cursor2);
            thread.getSmss().add(sms);
        }
        return threads;
    }

}
