package com.as_supportpe.notes.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.as_supportpe.notes.entities.Note;

import java.util.ArrayList;

/**
 * Created by marco on 05/01/16.
 */
public class NoteManager extends SQLiteOpenHelper {

    public static final int TEST_MAX_NOTES = 20; //TODO: Por eliminar
    public static int numNotes; //TODO: Por eliminar

    private static NoteManager instance;

    private final static String DATABASE_NAME = "notes";
    private final static int DATABASE_VERSION = 1;

    public final static String TABLE_NOTES = "notes";
    public final static String NOTES_COLUMN_ID = "_id";
    public final static String NOTES_COLUMN_TITLE = "title";
    public final static String NOTES_COLUMN_CONTENT = "content";
    public final static String NOTES_COLUMN_CREATION_TIMESTAMP = "creationTimestamp";

    public static NoteManager getInstance(final Context context) {
        if (instance == null) {
            instance = new NoteManager(context.getApplicationContext());
        }
        return instance;
    }

    private NoteManager(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Nullable @Deprecated
    public static ArrayList<Note> getNotes(){
        final ArrayList<Note> notes = new ArrayList<>();
        for (int i = 1; i <= TEST_MAX_NOTES; i++) {
            final Note note = new Note(i,"Título "+i,"Contenido "+i,System.currentTimeMillis());
            notes.add(note);
        }
        numNotes = TEST_MAX_NOTES;
        return notes;
    }

    public ArrayList<Note> getNotesDB(){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String query = "SELECT * FROM notes";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        ArrayList<Note> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            Note note = new Note(
                    cursor.getLong(cursor.getColumnIndex(NOTES_COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(NOTES_COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(NOTES_COLUMN_CONTENT)),
                    cursor.getLong(cursor.getColumnIndex(NOTES_COLUMN_CREATION_TIMESTAMP))
            );
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

    @Deprecated
    public static Response createNote(Note note) {
        String errorMsj = "";
        //TODO: Crear nueva nota, devolver ID (persistencia)
        numNotes++;//TODO: Por eliminar
        note.setId(numNotes);//TODO: Cambiar con el ID generado
        return new Response(note,errorMsj,true);
    }

    public Note createNoteDB(Note note){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_COLUMN_TITLE, note.getTitle());
        contentValues.put(NOTES_COLUMN_CONTENT, note.getContent());
        contentValues.put(NOTES_COLUMN_CREATION_TIMESTAMP, note.getTimestamp());
        long id = sqLiteDatabase.insert(TABLE_NOTES, null, contentValues);
        return new Note(id,note);
    }

    @Deprecated
    public static Response updateNote(Note note) {
        String errorMsj = "";
        //TODO: Actualizar nota usando ID (persistencia)
        return new Response(note,errorMsj,true);
    }

    public boolean updateNoteDB(Note note){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTES_COLUMN_TITLE, note.getTitle());
        contentValues.put(NOTES_COLUMN_CONTENT, note.getContent());
        contentValues.put(NOTES_COLUMN_CREATION_TIMESTAMP, note.getTimestamp());
        return sqLiteDatabase.update(
                TABLE_NOTES,
                contentValues,
                String.format("%s = ?", NOTES_COLUMN_ID),
                new String[]{String.valueOf(note.getId())}
        ) == 1;
    }

    @Deprecated
    public static Response deleteNote(Note note) {
        String errorMsj = "";
        //TODO: Eliminar nota usando ID (persistencia)
        return new Response(note,errorMsj,true);
    }

    public boolean deleteNoteDB(Note note){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete(
                TABLE_NOTES,
                String.format("%s = ?", NOTES_COLUMN_ID),
                new String[]{String.valueOf(note.getId())}
        ) == 1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String creationScript = "CREATE TABLE notes (_id INTEGER PRIMARY KEY, title TEXT, content TEXT, creationTimestamp INTEGER)";
        db.execSQL(creationScript);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
