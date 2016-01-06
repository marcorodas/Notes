package com.as_supportpe.notes.model;

import java.util.ArrayList;

/**
 * Created by marco on 05/01/16.
 */
public class NoteManager {
    public static ArrayList<Note> getNotes(){
        final ArrayList<Note> notes = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            final Note note = new Note(i,"Título "+i,"Contenido "+i,System.currentTimeMillis());
            notes.add(note);
        }
        return notes;
    }
}
