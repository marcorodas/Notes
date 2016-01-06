package com.as_supportpe.notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.as_supportpe.notes.model.Note;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by marco on 06/01/16.
 */
public class NoteListAdapter extends ArrayAdapter<Note> {
    public NoteListAdapter(final Context context, final List<Note> notes) {
        super(context, 0, notes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            //Se obtiene una nueva vista a partir de R.layout.element_note
            convertView = inflater.inflate(R.layout.element_note,parent,false);
            //Se obtienen los componentes de dicha vista
            viewHolder = new ViewHolder(
                    convertView
                    , R.id.list_element_date
                    , R.id.list_element_title
                    , R.id.list_element_content
            );
            //Se guardan los componentes de la vista en ésta
            convertView.setTag(viewHolder);
        }
        else{
            //Se recuperan los componentes de la vista desde ésta
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //Se modifican los componentes de la vista
        Note note = getItem(position);
        viewHolder.setNote(note);
        return convertView;
    }

    public static class ViewHolder {
        public TextView date;
        public TextView title;
        public TextView content;

        public ViewHolder(View containerView, int idResourceDate, int idResourceTitle, int idResourceContent) {
            this.date = (TextView) containerView.findViewById(idResourceDate);
            this.title = (TextView) containerView.findViewById(idResourceTitle);
            this.content = (TextView) containerView.findViewById(idResourceContent);
        }

        public void setNote(Note note){
            this.title.setText(note.getTitle());
            this.content.setText(note.getContent());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(note.getTimestamp());
            String dateText = DateFormat.getDateInstance().format(calendar.getTime());
            this.date.setText(dateText);
        }
    }
}
