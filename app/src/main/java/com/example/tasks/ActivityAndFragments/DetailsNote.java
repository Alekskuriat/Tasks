package com.example.tasks.ActivityAndFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasks.Observer;
import com.example.tasks.Publisher;
import com.example.tasks.PublisherHolder;
import com.example.tasks.R;
import com.example.tasks.notePackage.Note;
import com.example.tasks.notePackage.NotesRepository;


public class DetailsNote extends Fragment implements Observer {

    private static final String ARG_NOTE = "ARG_NOTE";
    private boolean isLandscape = false;
    private TextView title;
    private TextView noteDetails;
    private TextView dateAndTime;
    private TextView nameNote;
    private NotesRepository notesRepository;

    public DetailsNote() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        isLandscape = getResources().getBoolean(R.bool.isLandscape);
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
        MainActivity activity = (MainActivity) context;
        notesRepository = activity.getNotesRepository();
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameNote = view.findViewById(R.id.name_details);
        title = view.findViewById(R.id.title_details);
        noteDetails = view.findViewById(R.id.note_details);
        dateAndTime = view.findViewById(R.id.date_details);

        assert getArguments() != null;
        Note note = getArguments().getParcelable(ARG_NOTE);
        if (note != null) {
            nameNote.setText(note.getName());
            title.setText(getResources().getString(R.string.title_template, note.getSerialNumber()));
            noteDetails.setText(note.getContent());
            dateAndTime.setText(note.getDateTime());
        } else title.setText(getString(R.string.error));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateNote(Note note) {
        nameNote.setText(note.getName());
        title.setText(getResources().getString(R.string.title_template, note.getSerialNumber()));
        noteDetails.setText(note.getContent());
        dateAndTime.setText(note.getDateTime());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.details_notes_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (isLandscape) {
            FragmentManager fragmentManager = getParentFragmentManager();
            Fragment fr = fragmentManager.findFragmentById(R.id.details_fragment);
            Note note = fr.getArguments().getParcelable("ARG_NOTE");

            if (item.getItemId() == R.id.edit_btn_menu) {
                editNote();
            }

            if (item.getItemId() == R.id.delete_btn_menu) {
                int positionPreviousNote = notesRepository.getNotes().indexOf(note);
                notesRepository.deleteNote(note);
                if (notesRepository.getNote(1) != null && positionPreviousNote > 0) {
                    Note notePrevious = notesRepository.getNote(positionPreviousNote);
                    fragmentManager.beginTransaction()
                            .replace(R.id.list_fragment, new NotesList())
                            .replace(R.id.details_fragment, DetailsNote.newInstance(notePrevious))
                            .commit();
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.list_fragment, new NotesList())
                            .remove(fr)
                            .commit();
                }

            }

        } else {
            FragmentManager fragmentManager = getParentFragmentManager();
            Fragment fr = fragmentManager.findFragmentById(R.id.container);

            if (item.getItemId() == R.id.edit_btn_menu) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, NoteEditFragment.newInstance(fr.getArguments().getParcelable("ARG_NOTE")))
                        .addToBackStack(null)
                        .commit();
            }
            if (item.getItemId() == R.id.delete_btn_menu) {
                notesRepository.deleteNote(fr.getArguments().getParcelable("ARG_NOTE"));
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new NotesList())
                        .commit();
            }
        }

        if (getContext() != null)
            Save.save(getContext());
        return super.onOptionsItemSelected(item);


    }


    public void editNote() {
        FragmentManager fragmentManager = getParentFragmentManager();
        Fragment fr = fragmentManager.findFragmentById(R.id.details_fragment);
        if (fr != null) {
            if (fr.getArguments() != null) {
                Note note = fr.getArguments().getParcelable("ARG_NOTE");
                fragmentManager.beginTransaction()
                        .replace(R.id.details_fragment, NoteEditFragment.newInstance(note))
                        .commit();
            }
        }
    }

}
