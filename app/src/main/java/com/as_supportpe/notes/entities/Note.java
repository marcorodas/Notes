package com.as_supportpe.notes.entities;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by marco on 05/01/16.
 */
public class Note {
    private final long id;
    private final String title;
    private final String content;
    private final long timestamp;

    public Note(long id, String title, String content, long timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getTimestampAsString(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp);
        return DateFormat.getDateInstance().format(calendar.getTime());
    }
}
