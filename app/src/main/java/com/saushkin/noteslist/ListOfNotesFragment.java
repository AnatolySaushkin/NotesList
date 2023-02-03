package com.saushkin.noteslist;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ListOfNotesFragment extends Fragment {

    private boolean isLandscape;
    private Note[] notes;
    private Note currentNote;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_of_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initNotes(view);
    }

    private void initNotes(View view) {
        notes = new Note[]{
                new Note(getString(R.string.title01), getString(R.string.content01), Calendar.getInstance()),
                new Note(getString(R.string.title02), getString(R.string.content02), Calendar.getInstance()),
                new Note(getString(R.string.title03), getString(R.string.content03), Calendar.getInstance())
        };

        for (Note note : notes) {
            LinearLayout linearView = (LinearLayout) view;
            TextView tvTitle = new TextView(getContext());
            TextView tvDate = new TextView(getContext());
            tvTitle.setText(note.getNoteTitle());
            tvTitle.setTextSize(22);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
            tvDate.setText(formatter.format(note.getDateOfCreation().getTime()));
            linearView.addView(tvTitle);
            linearView.addView(tvDate);
            tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentNote = note;
                    showNote(currentNote);
                }
            });
        }
    }

    // activity создана, можно к ней обращаться. Выполним начальные действия
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(NoteFragment.CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(NoteFragment.CURRENT_NOTE);
        } else {
            currentNote = notes[0];
        }
        if (isLandscape) {
            showLandNote(currentNote);
        }
    }

    private void showNote(Note currentNote) {
        if (isLandscape) {
            showLandNote(currentNote);
        } else {
            showPortNote(currentNote);
        }
    }

    private void showLandNote(Note currentNote) {
        NoteFragment fragment = NoteFragment.newInstance(currentNote);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_for_notes, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    private void showPortNote(Note currentNote) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteActivity.class);
        intent.putExtra(NoteFragment.CURRENT_NOTE, currentNote);
        startActivity(intent);
    }
}