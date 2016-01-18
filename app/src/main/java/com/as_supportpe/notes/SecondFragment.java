package com.as_supportpe.notes;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.as_supportpe.notes.entities.Note;

/**
 * Created by marco on 05/01/16.
 */
public class SecondFragment extends Fragment implements View.OnClickListener {

    private Button btnSave;
    private Button btnDelete;
    private TextView txtDate;
    private EditText editTxtTitle;
    private EditText editTxtContent;
    private Note note;
    private OnBtnClickListener onBtnClickListener;
    private int position;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_second, container, false);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        editTxtTitle = (EditText) view.findViewById(R.id.editTxt_Title);
        editTxtContent = (EditText) view.findViewById(R.id.editTxt_Content);
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(onBtnClickListener!=null){
            switch (v.getId()){
                case R.id.btnSave:
                    if(noteHasPendingChanges()){
                        note = new Note(
                                note.getId(),
                                editTxtTitle.getText().toString().trim(),
                                editTxtContent.getText().toString().trim(),
                                note.getTimestamp()
                        );
                        onBtnClickListener.btnSaveOnClick(note,position);
                    }
                    break;
                case R.id.btnDelete:
                    onBtnClickListener.btnDeleteOnClick(note,position);
                    break;
            }
        }
    }

    private boolean noteHasPendingChanges(){
        String txtTitle = editTxtTitle.getText().toString().trim();
        String txtContext = editTxtContent.getText().toString().trim();
        return !txtTitle.equals(note.getTitle()) && !txtContext.equals(note.getContent());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (note!=null){
            setVisibility(true);
            displayNote();
        }
    }

    public void setOnBtnClickListener(OnBtnClickListener onBtnClickListener) {
        this.onBtnClickListener = onBtnClickListener;
    }

    public void setNote(Note note, int position) {
        this.note = note;
        this.position = position;
    }

    public void setVisibility(final boolean isVisible){
        final int visibility = isVisible ? View.VISIBLE : View.INVISIBLE;
        View[] views = {btnSave, btnDelete,txtDate,editTxtTitle,editTxtContent};
        for (View view: views) {
            view.setVisibility(visibility);
        }
    }
    
    public void displayNote(){
        btnDelete.setVisibility(
                note.getId() == Note.NEW_NOTE_ID ?
                        View.INVISIBLE :
                        View.VISIBLE
        );
        txtDate.setText(note.getTimestampAsString());
        editTxtTitle.setText(note.getTitle());
        editTxtContent.setText(note.getContent());
    }

    public interface OnBtnClickListener{
        void btnSaveOnClick(Note note, int position);
        void btnDeleteOnClick(Note note, int position);
    }
}