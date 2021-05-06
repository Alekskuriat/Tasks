package com.example.tasks.ActivityAndFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tasks.R;
import com.example.tasks.notePackage.Note;

public class NoteCreateFragments extends Fragment {




    private EditText nameNote;
    private EditText contentNote;
    private TextView numberNote;
    private Button createNote;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nameNote = view.findViewById(R.id.name_create);
        contentNote = view.findViewById(R.id.note_details_create);
        numberNote = view.findViewById(R.id.title_create);
        createNote = view.findViewById(R.id.btn_create_note);


        numberNote.setText(getString(R.string.note_number).concat(String.valueOf(Integer.parseInt(NotesList.notes.getLastNumberNote()) + 1)));
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotesList.notes.setNote(new Note(nameNote.getText().toString(), contentNote.getText().toString(), Integer.parseInt(NotesList.notes.getLastNumberNote())));
                Save.save(getActivity());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_create_fragment, container, false);
    }
}
