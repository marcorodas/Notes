package com.as_supportpe.notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.as_supportpe.notes.entities.Note;
import com.as_supportpe.notes.model.NoteManager;

import java.util.List;

/**
 * Created by marco on 05/01/16.
 */
public class FirstFragment extends Fragment {

    private NoteManager noteManager;
    private NoteListAdapter noteListAdapter;
    private ListView listView;
    private ActionListener actionListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        noteManager = NoteManager.getInstance(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        listView = (ListView) view.findViewById(R.id.listview_note);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final List<Note> list = noteManager.getNotes();
        noteListAdapter = new NoteListAdapter(getActivity(), list);
        listView.setAdapter(noteListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (actionListener != null) {
                    actionListener.onNoteSelected(list.get(position), position);
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_first, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionListener != null) {
            switch (item.getItemId()) {
                case R.id.menu_btnNewNote:
                    actionListener.btnNewOnClick(noteManager, noteListAdapter);
                    return true;
            }
        }
        return false;
    }

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public Note addNote(Note note) {
        note = noteManager.createNote(note);
        boolean responseOk = note.getId() != NoteManager.NEW_NOTE_ID;
        if (responseOk) {
            noteListAdapter.insert(note, 0);
            noteListAdapter.notifyDataSetChanged();
        }
        return responseOk ? note : null;
    }

    public boolean updateNote(Note note, int position) {
        boolean responseOk = noteManager.updateNote(note);
        if (responseOk) {
            Note noteFromAdapter = noteListAdapter.getItem(position);
            noteFromAdapter.setTitle(note.getTitle());
            noteFromAdapter.setContent(note.getContent());
            noteListAdapter.notifyDataSetChanged();
        }
        return responseOk;
    }

    public boolean removeNote(Note note, int position) {
        boolean responseOk = noteManager.deleteNote(note);
        if (responseOk) {
            noteListAdapter.remove(noteListAdapter.getItem(position));
            noteListAdapter.notifyDataSetChanged();
        }
        return responseOk;
    }

    public interface ActionListener {
        void onNoteSelected(final Note note, int position);

        void btnNewOnClick(NoteManager noteManager, NoteListAdapter noteListAdapter);
    }
}
