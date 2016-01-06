package com.as_supportpe.notes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.as_supportpe.notes.model.Note;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final NoteList noteList = new NoteList();
        noteList.setOnNoteListener(new NoteList.OnNoteListener() {
            @Override
            public void onNoteSelected(Note note) {
                Toast.makeText(MainActivity.this, note.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
