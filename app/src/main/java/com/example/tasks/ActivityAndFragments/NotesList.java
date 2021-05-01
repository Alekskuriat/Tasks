package com.example.tasks.ActivityAndFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tasks.OnNoteClicked;
import com.example.tasks.PublisherHolder;
import com.example.tasks.R;
import com.example.tasks.notePackage.Note;
import com.example.tasks.notePackage.NotesRepository;

import java.util.List;

public class NotesList extends Fragment {

    private OnNoteClicked onNoteClicked;
    NotesRepository notes;
    TextView nameNote;
    TextView numberNote;
    TextView content;
    TextView dateAndTime;

    public NotesList() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnNoteClicked) {
            onNoteClicked = (OnNoteClicked) context;
        }
    }

    @Override
    public void onDetach() {
        onNoteClicked = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notes = new NotesRepository();

        LinearLayout notesList = view.findViewById(R.id.notes_list);

        for (Note note : notes.getNotes()) {

            View noteView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_note, notesList, false);

            noteView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openNoteDetail(note);
                }
            });

            nameNote = noteView.findViewById(R.id.name_view);
            nameNote.setText(note.getName());

            numberNote = noteView.findViewById(R.id.numbers_view);
            numberNote.setText("№" + note.getSerialNumber());

            content = noteView.findViewById(R.id.task_view);
            content.setText(note.getContent());

            dateAndTime = noteView.findViewById(R.id.data_view);
            dateAndTime.setText(note.getDateTime());

            notesList.addView(noteView);
        }

    }

    private void openNoteDetail(Note note) {
        if (getActivity() instanceof PublisherHolder) {
            PublisherHolder holder = (PublisherHolder) getActivity();
            holder.getPublisher().notify(note);
        }

        if (onNoteClicked != null) {
            onNoteClicked.onNoteClicked(note);
        }
    }
}