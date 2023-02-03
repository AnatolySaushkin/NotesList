package com.saushkin.noteslist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class NoteFragment extends Fragment {

    static final String CURRENT_NOTE = "currentNote";
    private Note note;

    // Фабричный метод создания фрагмента
    public static NoteFragment newInstance(Note note) {
        // создание
        NoteFragment noteFragment = new NoteFragment();

        // Передача параметра
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, note);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(CURRENT_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        TextView noteTitle = view.findViewById(R.id.note_title);
        TextView noteContent = view.findViewById(R.id.note_content);
        TextView dateOfCreation = view.findViewById(R.id.note_date_of_creation);
        noteTitle.setText(note.getNoteTitle());
        noteContent.setText(note.getNoteContent());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        dateOfCreation.setText(String.format("%s", formatter.format(note.getDateOfCreation().getTime())));
        return view;
    }
}