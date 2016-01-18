package com.as_supportpe.notes;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.as_supportpe.notes.entities.Note;

/**
 * Created by marco on 05/01/16.
 */
public class SecondFragment extends Fragment {

    private TextView txtDate;
    private EditText editTxtTitle;
    private EditText editTxtContent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_second, container, false);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        editTxtTitle = (EditText) view.findViewById(R.id.editTxt_Title);
        editTxtContent = (EditText) view.findViewById(R.id.editTxt_Content);
        return view;
    }

    public void setNote(Note note){
        txtDate.setText(note.getTimestampAsString());
        editTxtTitle.setText(note.getTitle());
        editTxtContent.setText(note.getContent());
    }
}