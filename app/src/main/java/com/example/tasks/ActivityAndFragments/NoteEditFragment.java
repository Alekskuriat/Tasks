package com.example.tasks.ActivityAndFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tasks.R;
import com.example.tasks.notePackage.Note;

public class NoteEditFragment extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";
    private boolean isLandscape = false;
    private EditText nameNote;
    private EditText contentNote;
    private TextView numberNote;
    private Button createNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandscape = getResources().getBoolean(R.bool.isLandscape);
    }

    public static NoteEditFragment newInstance(Note note) {
        NoteEditFragment fragment = new NoteEditFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARG_NOTE, note);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameNote = view.findViewById(R.id.name_edit);
        contentNote = view.findViewById(R.id.note_details_edit);
        numberNote = view.findViewById(R.id.title_edit);
        createNote = view.findViewById(R.id.btn_create_note);
        Note note = getArguments().getParcelable(ARG_NOTE);


        numberNote.setText(getString(R.string.note_number).concat(note.getSerialNumber()));
        nameNote.setText(note.getName());
        contentNote.setText(note.getContent());
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note newNote = new Note(String.valueOf(nameNote.getText()), String.valueOf(contentNote.getText()), Integer.parseInt(note.getSerialNumber()) - 1);
                if (isLandscape) {
                    NotesList.notes.editNote(newNote);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.list_fragment, new NotesList())
                            .replace(R.id.details_fragment, DetailsNote.newInstance(newNote))
                            .commit();

                } else {
                    NotesList.notes.editNote(newNote);
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, DetailsNote.newInstance(newNote))
                            .commit();

                }
                Save.save(getActivity());
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_edit_fragment, container, false);
    }
}



