package fr.pinguet62.croquette.android.sms.database;

import java.util.LinkedList;
import java.util.List;

import android.database.Cursor;

public final class Thread {

    private final int _id;
    private final int date;
    private final int error;
    private final int has_attachment;
    private final int read;
    private final String recipient_ids;
    private final List<Sms> smss = new LinkedList<Sms>();
    private final int snippet_cs;

    private final int type;

    public Thread(Cursor cursor) {
        _id = cursor.getInt(cursor.getColumnIndex("_id"));
        date = cursor.getInt(cursor.getColumnIndex("date"));
        recipient_ids = cursor
                .getString(cursor.getColumnIndex("recipient_ids"));
        snippet_cs = cursor.getInt(cursor.getColumnIndex("snippet_cs"));
        read = cursor.getInt(cursor.getColumnIndex("read"));
        type = cursor.getInt(cursor.getColumnIndex("type"));
        error = cursor.getInt(cursor.getColumnIndex("error"));
        has_attachment = cursor.getInt(cursor.getColumnIndex("has_attachment"));
    }

    public int getDate() {
        return date;
    }

    public int getError() {
        return error;
    }

    public int getHasAttachment() {
        return has_attachment;
    }

    public int getId() {
        return _id;
    }

    public int getRead() {
        return read;
    }

    public String getRecipientIds() {
        return recipient_ids;
    }

    /**
     * @return The {@link Sms}.<br>
     *         An empty {@link List} by default.
     */
    public List<Sms> getSmss() {
        return smss;
    }

    public int getSnippetCs() {
        return snippet_cs;
    }

    public int getType() {
        return type;
    }
}
