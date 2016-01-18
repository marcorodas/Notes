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
    private ActionListener actionListener;
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
        if (actionListener != null && notes != null){
            final Note note = notes.get(position);
            actionListener.onNoteSelected(note,position);
        }
    }

    @Override
    public void onClick(View v) {
        if (actionListener !=null){
            switch (v.getId()){
                case R.id.btnNewNote:
                    actionListener.btnNewOnClick();
                    break;
            }
        }
    }

    public void setNotes(List<Note> notes) {this.notes = notes;}

    public void setActionListener(ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public interface ActionListener {
        void onNoteSelected(final Note note, int position);
        void btnNewOnClick();
    }

    public void addNote(Note note){
        noteListAdapter.insert(note,0);
        noteListAdapter.notifyDataSetChanged();
    }

    public void updateNote(Note note, int position){
        final Note noteFromAdapter = noteListAdapter.getItem(position);
        noteFromAdapter.setTitle(note.getTitle());
        noteFromAdapter.setContent(note.getContent());
        noteListAdapter.notifyDataSetChanged();
    }

    public void removeNote(int position){
        noteListAdapter.remove(noteListAdapter.getItem(position));
        noteListAdapter.notifyDataSetChanged();
    }

}
