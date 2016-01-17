package com.as_supportpe.notes;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.as_supportpe.notes.entities.Note;

public class MainActivity extends AppCompatActivity implements FirstFragment.OnNoteListener {

    final FirstFragment firstFragment = new FirstFragment();
    final SecondFragment secondFragment = new SecondFragment();
    boolean isDualPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firstFragment.setOnNoteListener(this);
        isDualPanel = findViewById(R.id.tablet_container) != null;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(isDualPanel ? R.id.first_fragment_container : R.id.handset_container, firstFragment);
        if (isDualPanel) {
            transaction.replace(R.id.second_fragment_container, secondFragment);
        }
        transaction.commit();
    }

    @Override
    public void onNoteSelected(Note note) {
        if (isDualPanel) {
            secondFragment.setNote(note);
        }
        else{
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.handset_container,secondFragment)
                    .addToBackStack(null)
                    .commit();
        }
    }
}
