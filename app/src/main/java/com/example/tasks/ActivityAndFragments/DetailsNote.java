package com.example.tasks.ActivityAndFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tasks.Observer;
import com.example.tasks.Publisher;
import com.example.tasks.PublisherHolder;
import com.example.tasks.R;
import com.example.tasks.notePackage.Note;


public class DetailsNote extends Fragment implements Observer {

    private static final String ARG_NOTE = "ARG_NOTE";
    private TextView title;
    private TextView noteDetails;
    private TextView dateAndTime;
    private TextView nameNote;

    public DetailsNote() {

    }

    public static DetailsNote newInstance(Note note) {
        DetailsNote fragment = new DetailsNote();

        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);

        fragment.setArguments(bundle);
        return fragment;
    }

    private Publisher publisher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PublisherHolder) {
            publisher = ((PublisherHolder) context).getPublisher();
            publisher.addObserver(this);
        }
    }

    @Override
    public void onDetach() {
        if (publisher != null) {
            publisher.removeObserver(this);
        }
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameNote = view.findViewById(R.id.name_details);
        title = view.findViewById(R.id.title_details);
        noteDetails = view.findViewById(R.id.note_details);
        dateAndTime = view.findViewById(R.id.date_details);

        Note note = getArguments().getParcelable(ARG_NOTE);

        nameNote.setText(note.getName());
        title.setText("Заметка №" + note.getSerialNumber());
        noteDetails.setText(note.getContent());
        dateAndTime.setText(note.getDateTime());
    }


    @Override
    public void updateNote(Note note) {
        nameNote.setText(note.getName());
        title.setText("Заметка №" + note.getSerialNumber());
        noteDetails.setText(note.getContent());
        dateAndTime.setText(note.getDateTime());
    }
}
