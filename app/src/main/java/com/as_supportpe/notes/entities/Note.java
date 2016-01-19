package com.as_supportpe.notes.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * Created by marco on 05/01/16.
 */
public class Note implements Parcelable{
    public static final int NEW_NOTE_ID = -1;
    private long id;
    private String title;
    private String content;
    private final long timestamp;

    public Note(long id, String title, String content, long timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
    }

    public Note() {
        this.id = NEW_NOTE_ID;
        this.title = "";
        this.content = "";
        this.timestamp = System.currentTimeMillis();
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(long id) {
        this.id = id;
    }

    private Note(Parcel in){
        this.id = in.readLong();
        this.title = in.readString();
        this.content = in.readString();
        this.timestamp = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeLong(timestamp);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Note> CREATOR = new Parcelable.Creator<Note>() {
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
