package com.as_supportpe.notes;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.as_supportpe.notes.entities.Note;
import com.as_supportpe.notes.model.NoteManager;


public class MainActivity extends AppCompatActivity implements FirstFragment.ActionListener {

    private static final String FIRST_FRAGMENT_TAG = "list_fragment";
    private static final String SECOND_FRAGMENT_TAG = "content_fragment";
    private final String SECOND_FRAGMENT_ISVISIBLE = "isVisible";
    private final FragmentManager fragmentManager = getSupportFragmentManager();
    private boolean isDualPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isDualPanel = findViewById(R.id.tablet_container) != null;

        FirstFragment firstFragment = (savedInstanceState == null) ?
                new FirstFragment() : getFirstFragment();
        SecondFragment secondFragment = (savedInstanceState == null) ?
                SecondFragment.getInstance(new Note(), 0) : getSecondFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (isDualPanel) {
            transaction
                    .replace(R.id.first_fragment_container, firstFragment, FIRST_FRAGMENT_TAG)
                    .replace(R.id.second_fragment_container, secondFragment, SECOND_FRAGMENT_TAG);
        } else {
            transaction
                    .replace(R.id.handset_container, firstFragment, FIRST_FRAGMENT_TAG);
        }
        transaction.commit();
        firstFragment.setActionListener(this);
    }

    private FirstFragment getFirstFragment() {
        return (FirstFragment) fragmentManager.findFragmentByTag(FIRST_FRAGMENT_TAG);
    }

    private SecondFragment getSecondFragment() {
        return (SecondFragment) fragmentManager.findFragmentByTag(SECOND_FRAGMENT_TAG);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!isDualPanel) {
            SecondFragment secondFragment = getSecondFragment();
            boolean secondFragmentIsVisible = secondFragment != null && secondFragment.isVisible();
            outState.putBoolean(SECOND_FRAGMENT_ISVISIBLE, secondFragmentIsVisible);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (!isDualPanel) {
            boolean secondFragmentIsVisible = savedInstanceState.getBoolean(SECOND_FRAGMENT_ISVISIBLE);
            if (secondFragmentIsVisible) {
                SecondFragment secondFragment = getSecondFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.handset_container, secondFragment, SECOND_FRAGMENT_TAG)
                        .commit();
            }
        }
    }

    @Override
    public void onNoteSelected(Note note, int position) {
        SecondFragment secondFragment = SecondFragment.getInstance(note, position);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int id_container = isDualPanel ? R.id.second_fragment_container : R.id.handset_container;
        transaction.replace(id_container, secondFragment, SECOND_FRAGMENT_TAG);
        if (!isDualPanel) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void btnNewOnClick(NoteManager noteManager, NoteListAdapter noteListAdapter) {
        onNoteSelected(new Note(), 0);
    }

    public void btnSaveOnClick(Note note, int position) {
        FirstFragment firstFragment = (FirstFragment) fragmentManager.findFragmentByTag(FIRST_FRAGMENT_TAG);
        String message = null;
        boolean isNewNote = note.getId() == NoteManager.NEW_NOTE_ID;
        boolean isResponseOk = isNewNote ?
                firstFragment.addNote(note) :
                firstFragment.updateNote(note, position);
        if (isResponseOk) {
            if (!isDualPanel) {
                fragmentManager.popBackStack();
            }
            message = String.format("Nota '%s' ", note.getTitle())
                    .concat(isNewNote ? "Creada" : "Guardada");
        }
        showMessage(message);
    }

    public void btnDeleteOnClick(Note note, int position) {
        FirstFragment firstFragment = (FirstFragment) fragmentManager.findFragmentByTag(FIRST_FRAGMENT_TAG);
        boolean isResponseOk = firstFragment.removeNote(note, position);
        String message = null;
        if (isResponseOk) {
            if (!isDualPanel) {
                fragmentManager.popBackStack();
            }
            message = String.format("Nota '%s' Eliminada", note.getTitle());
        }
        showMessage(message);
    }

    public void showMessage(String message) {
        message = message == null ? "¡Ha ocurrido un error!" : message;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}