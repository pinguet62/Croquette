package fr.pinguet62.croquette.android.sms.database;

import android.database.Cursor;

public final class Sms {

    private final int _id;
    private final String address;
    private final String body;
    private final int date;
    private final int date_sent;
    private final int error_code;
    private final int locked;
    private final int person;
    private final int protocol;
    private final int read;
    private final int reply_path_present;
    private final int seen;
    private final String service_center;
    private final int status;
    private final String subject;
    private final int thread_id;
    private final int type;

    public Sms(Cursor cursor) {
        _id = cursor.getInt(cursor.getColumnIndex("_id"));
        thread_id = cursor.getInt(cursor.getColumnIndex("thread_id"));
        address = cursor.getString(cursor.getColumnIndex("address"));
        person = cursor.getInt(cursor.getColumnIndex("person"));
        // TODO convert to Date
        date = cursor.getInt(cursor.getColumnIndex("date"));
        // TODO convert to Date
        date_sent = cursor.getInt(cursor.getColumnIndex("date_sent"));
        protocol = cursor.getInt(cursor.getColumnIndex("protocol"));
        read = cursor.getInt(cursor.getColumnIndex("read"));
        status = cursor.getInt(cursor.getColumnIndex("status"));
        type = cursor.getInt(cursor.getColumnIndex("type"));
        reply_path_present = cursor.getInt(cursor
                .getColumnIndex("replyPathPresent"));
        subject = cursor.getString(cursor.getColumnIndex("subject"));
        body = cursor.getString(cursor.getColumnIndex("body"));
        service_center = cursor.getString(cursor
                .getColumnIndex("serviceCenter"));
        locked = cursor.getInt(cursor.getColumnIndex("locked"));
        error_code = cursor.getInt(cursor.getColumnIndex("errorCode"));
        seen = cursor.getInt(cursor.getColumnIndex("seen"));
    }

    public String getAddress() {
        return address;
    }

    public String getBody() {
        return body;
    }

    public int getDate() {
        return date;
    }

    public int getDateSent() {
        return date_sent;
    }

    public int getErrorCode() {
        return error_code;
    }

    public int getId() {
        return _id;
    }

    public int getLocked() {
        return locked;
    }

    public int getPerson() {
        return person;
    }

    public int getProtocol() {
        return protocol;
    }

    public int getRead() {
        return read;
    }

    public int getReplyPathPresent() {
        return reply_path_present;
    }

    public int getSeen() {
        return seen;
    }

    public String getServiceCenter() {
        return service_center;
    }

    public int getStatus() {
        return status;
    }

    public String getSubject() {
        return subject;
    }

    public int getThreadId() {
        return thread_id;
    }

    public int getType() {
        return type;
    }

}
