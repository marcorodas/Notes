package com.as_supportpe.notes.model;

import android.support.annotation.Nullable;

import com.as_supportpe.notes.entities.Note;

import java.util.ArrayList;

/**
 * Created by marco on 05/01/16.
 */
public class NoteManager {

    public static final int TEST_MAX_NOTES = 20; //TODO: Por eliminar
    public static int numNotes; //TODO: Por eliminar

    @Nullable
    public static ArrayList<Note> getNotes(){
        final ArrayList<Note> notes = new ArrayList<>();
        for (int i = 1; i <= TEST_MAX_NOTES; i++) {
            final Note note = new Note(i,"Título "+i,"Contenido "+i,System.currentTimeMillis());
            notes.add(note);
        }
        numNotes = TEST_MAX_NOTES;
        return notes;
    }

    public static Response createNote(Note note) {
        String errorMsj = "";
        //TODO: Crear nueva nota, devolver ID (persistencia)
        numNotes++;//TODO: Por eliminar
        note.setId(numNotes);//TODO: Cambiar con el ID generado
        return new Response(note,errorMsj,true);
    }

    public static Response updateNote(Note note) {
        String errorMsj = "";
        //TODO: Actualizar nota usando ID (persistencia)
        return new Response(note,errorMsj,true);
    }

    public static Response deleteNote(Note note) {
        String errorMsj = "";
        //TODO: Eliminar nota usando ID (persistencia)
        return new Response(note,errorMsj,true);
    }
}
