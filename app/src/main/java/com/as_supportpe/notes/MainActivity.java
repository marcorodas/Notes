package com.as_supportpe.notes;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.as_supportpe.notes.entities.Note;
import com.as_supportpe.notes.model.NoteManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity
        extends AppCompatActivity
        implements FirstFragment.OnNoteListener, SecondFragment.OnBtnClickListener {

    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private final FirstFragment firstFragment = new FirstFragment();
    private final SecondFragment secondFragment = new SecondFragment();
    private boolean isDualPanel;
    public static int FLAG_NEW_NOTE_ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isDualPanel = findViewById(R.id.tablet_container) != null;

        final List<Note> notes = NoteManager.getNotes();
        firstFragment.setNotes(notes);
        firstFragment.setOnNoteListener(this);
        secondFragment.setOnBtnClickListener(this);

        if (notes != null && !notes.isEmpty()) {
            secondFragment.setNote(notes.get(0));
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
    public void onNoteSelected(Note note) {
        secondFragment.setNote(note);
        if (isDualPanel) {
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
    public void btnSaveOnClick(Note note) {
        //TODO: Actualizar por ID (persistencia)
        //TODO: Actualizar FirstFragment (update)
    }

    @Override
    public void btnDeleteOnClick(Note note) {
        //TODO: Eliminar por ID (persistencia)
        //TODO: Actualizar FirstFragment (delete)
        if (isDualPanel) {
            secondFragment.setVisibility(false);
        } else {
            fragmentManager.popBackStack();
        }
    }
}