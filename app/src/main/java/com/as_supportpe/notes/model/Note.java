package com.as_supportpe.notes.model;

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
}
