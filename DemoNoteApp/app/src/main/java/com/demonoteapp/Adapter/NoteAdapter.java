package com.demonoteapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.demonoteapp.Model.Note;
import com.demonoteapp.R;

import java.util.List;

/**
 * Created by Administrator on 2/4/2017.
 */

public class NoteAdapter extends ArrayAdapter<Note> {
    List<Note> noteList;
    Context context;

    public NoteAdapter(Context context, int resource, List<Note> noteList) {
        super(context, resource);
        this.context = context;
        this.noteList = noteList;
    }

    @Override
    public int getCount() {
        return noteList.size();
    }

    @Override
    public Note getItem(int position) {
        return noteList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_note, parent, false);

        //init controls
        TextView tvNoteTitle, tvNoteContent;
        tvNoteTitle = (TextView) convertView.findViewById(R.id.tvNoteTitle);
        tvNoteContent = (TextView) convertView.findViewById(R.id.tvNoteContent);

        Note note = noteList.get(position);
        tvNoteTitle.setText(note.noteTitle);
        tvNoteContent.setText(note.noteContent);

        return convertView;

    }


}
