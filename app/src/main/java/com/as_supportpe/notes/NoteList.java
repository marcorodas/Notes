package com.as_supportpe.notes;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.as_supportpe.notes.model.Note;
import com.as_supportpe.notes.model.NoteManager;

import java.util.List;

/**
 * Created by marco on 05/01/16.
 */
public class NoteList extends ListFragment{

    private final List<Note> notes = NoteManager.getNotes();
    private OnNoteListener onNoteListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_note, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NoteListAdapter noteListAdapter = new NoteListAdapter(getActivity(),notes);
        setListAdapter(noteListAdapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (onNoteListener != null){
            final Note note = notes.get(position);
            onNoteListener.onNoteSelected(note);
        }
    }

    public void setOnNoteListener(OnNoteListener onNoteListener) {
        this.onNoteListener = onNoteListener;
    }

    public interface OnNoteListener{
        void onNoteSelected(final Note note);
    }
}
