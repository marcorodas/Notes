package com.as_supportpe.notes;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.as_supportpe.notes.entities.Note;
import com.as_supportpe.notes.model.NoteManager;
import com.as_supportpe.notes.model.Response;

import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements
        FirstFragment.ActionListener,
        SecondFragment.OnBtnClickListener {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final FirstFragment firstFragment = new FirstFragment();
    private final SecondFragment secondFragment = new SecondFragment();
    private boolean isDualPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isDualPanel = findViewById(R.id.tablet_container) != null;

        final List<Note> notes = NoteManager.getNotes();
        firstFragment.setNotes(notes);
        firstFragment.setActionListener(this);
        secondFragment.setOnBtnClickListener(this);

        if (notes != null && !notes.isEmpty()) {
            secondFragment.setNote(notes.get(0), 0);
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isDualPanel) {
            transaction
                    .replace(R.id.first_fragment_container, firstFragment)
                    .replace(R.id.second_fragment_container, secondFragment);
        } else {
            transaction
                    .replace(R.id.handset_container, firstFragment);
        }
        transaction.commit();
    }

    @Override
    public void onNoteSelected(Note note, int position) {
        secondFragment.setNote(note, position);
        if (isDualPanel) {
            secondFragment.setVisibility(true);
            secondFragment.displayNote();
        } else {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.handset_container, secondFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void btnNewOnClick() {
        onNoteSelected(new Note(), 0);
    }

    @Override
    public void btnSaveOnClick(Note note, int position) {
        Boolean isNewNote = (note.getId() == Note.NEW_NOTE_ID);
        Response response = isNewNote ?
                NoteManager.createNote(note)
                : NoteManager.updateNote(note);
        note = (Note) response.getOutputObj();
        if (response.isOK()) {
            if (isNewNote) {
                firstFragment.addNote(note);
            } else {
                firstFragment.updateNote(note, position);
            }
        }
        showMessage(
                response.isOK() ?
                        "Nota '%1' %2"
                                .replace("%2", isNewNote ? "Creada" : "Modificada")
                                .replace("%1", note.getTitle())
                        : response.getErrorMessage()
        );
    }

    @Override
    public void btnDeleteOnClick(Note note, int position) {
        Response response = NoteManager.deleteNote(note);
        if (response.isOK()) {
            firstFragment.removeNote(position);
            if (isDualPanel) {
                secondFragment.setVisibility(false);
            } else {
                fragmentManager.popBackStack();
            }
        }
        showMessage(
                response.isOK() ?
                "Nota '%1' Eliminada".replace("%1", note.getTitle())
                : response.getErrorMessage());
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}