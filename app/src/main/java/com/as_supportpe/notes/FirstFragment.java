package com.as_supportpe.notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.as_supportpe.notes.entities.Note;

import java.util.List;

/**
 * Created by marco on 05/01/16.
 */
public class FirstFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener{

    private ListView listView;
    private Button btnNewNote;
    private List<Note> notes;
    private OnNoteListener onNoteListener;
    private NoteListAdapter noteListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_first, container, false);
        listView = (ListView) view.findViewById(R.id.listview_note);
        btnNewNote = (Button) view.findViewById(R.id.btnNewNote);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (notes!=null){
            noteListAdapter = new NoteListAdapter(getActivity(),notes);
            listView.setAdapter(noteListAdapter);
            listView.setOnItemClickListener(this);
        }
        btnNewNote.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (onNoteListener != null && notes != null){
            final Note note = notes.get(position);
            onNoteListener.onNoteSelected(note);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnNewNote){
            final Note note = new Note(MainActivity.FLAG_NEW_NOTE_ID, "", "", System.currentTimeMillis());
            ((MainActivity) getActivity()).onNoteSelected(note);
        }
    }

    public void setNotes(List<Note> notes) {this.notes = notes;}

    public void setOnNoteListener(OnNoteListener onNoteListener) {
        this.onNoteListener = onNoteListener;
    }

    public interface OnNoteListener{
        void onNoteSelected(final Note note);
    }

}
