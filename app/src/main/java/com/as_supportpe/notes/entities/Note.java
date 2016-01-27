package com.as_supportpe.notes.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.as_supportpe.notes.model.NoteManager;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by marco on 05/01/16.
 */
public class Note implements Parcelable {
    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
    private final long timestamp;
    private long id;
    private String title;
    private String content;

    public Note(long id, String title, String content, long timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Note(long id, Note note) {
        this.id = id;
        this.title = note.getTitle();
        this.content = note.getContent();
        this.timestamp = note.getTimestamp();
    }

    public Note() {
        this.id = NoteManager.NEW_NOTE_ID;
        this.title = "";
        this.content = "";
        this.timestamp = System.currentTimeMillis();
    }

    protected Note(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
        timestamp = in.readLong();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getTimestampAsString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mm:ss:SSS", Locale.US);
        return sdf.format(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeLong(timestamp);
    }
}
