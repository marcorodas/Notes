package com.as_supportpe.notes;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.as_supportpe.notes.entities.Note;
import com.as_supportpe.notes.model.NoteManager;

/**
 * Created by marco on 05/01/16.
 */
public class SecondFragment extends Fragment {

    private static final String NOTE = "note";
    private static final String POSITION = "position";

    private TextView txtDate;
    private EditText editTxtTitle;
    private EditText editTxtContent;

    public static SecondFragment getInstance(Note note, int position) {
        SecondFragment secondFragment = new SecondFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE, note);
        bundle.putInt(POSITION, position);
        secondFragment.setArguments(bundle);
        return secondFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_second, container, false);
        txtDate = (TextView) view.findViewById(R.id.txtDate);
        editTxtTitle = (EditText) view.findViewById(R.id.editTxt_Title);
        editTxtContent = (EditText) view.findViewById(R.id.editTxt_Content);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_second, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.menu_btnDelete);
        Note note = getArguments().getParcelable(NOTE);
        item.setVisible(note.getId() != NoteManager.NEW_NOTE_ID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        MainActivity mainActivity = ((MainActivity) getActivity());
        Note note = getArguments().getParcelable(NOTE);
        int position = getArguments().getInt(POSITION);
        switch (item.getItemId()) {
            case R.id.menu_btnSave:
                if (noteHasPendingChanges(note)) {
                    note = new Note(
                            note.getId(),
                            editTxtTitle.getText().toString(),
                            editTxtContent.getText().toString(),
                            note.getTimestamp()
                    );
                    mainActivity.btnSaveOnClick(note, position);
                } else {
                    mainActivity.showMessage("Sin cambios");
                    mainActivity.getSupportFragmentManager().popBackStack();
                }
                return true;
            case R.id.menu_btnDelete:
                mainActivity.btnDeleteOnClick(note, position);
                return true;
        }
        return false;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showNote();
    }

    private void showNote() {
        Note note = getArguments().getParcelable(NOTE);
        txtDate.setText(note.getTimestampAsString());
        editTxtTitle.setText(note.getTitle());
        editTxtContent.setText(note.getContent());
    }

    private boolean noteHasPendingChanges(Note note) {
        String txtTitle = editTxtTitle.getText().toString().trim();
        String txtContext = editTxtContent.getText().toString().trim();
        return !txtTitle.equals(note.getTitle()) || !txtContext.equals(note.getContent());
    }

    @Override
    public void onPause() {
        super.onPause();
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        IBinder iBinder = getView().getWindowToken();
        if (iBinder != null) {
            imm.hideSoftInputFromWindow(iBinder, 0);
        }
    }
}