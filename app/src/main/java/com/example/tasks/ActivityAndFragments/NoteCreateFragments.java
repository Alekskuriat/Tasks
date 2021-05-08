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

import com.example.tasks.notePackage.NotesAdapter;
import com.example.tasks.R;
import com.example.tasks.notePackage.Note;

public class NoteCreateFragments extends Fragment {


    private boolean isLandscape = false;
    private EditText nameNote;
    private EditText contentNote;
    private TextView numberNote;
    private Button createNote;
    private NotesAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandscape = getResources().getBoolean(R.bool.isLandscape);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new NotesAdapter(this);
        nameNote = view.findViewById(R.id.name_create);
        contentNote = view.findViewById(R.id.note_details_create);
        numberNote = view.findViewById(R.id.title_create);
        createNote = view.findViewById(R.id.btn_create_note);


        numberNote.setText(getString(R.string.note_number).concat(String.valueOf(Integer.parseInt(NotesList.notes.getLastNumberNote()) + 1)));
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(nameNote.getText().toString(), contentNote.getText().toString(), Integer.parseInt(NotesList.notes.getLastNumberNote()));
                if (isLandscape) {
                    adapter.addData(note);
                    Save.save(getActivity());
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.details_fragment, DetailsNote.newInstance(note))
                            .replace(R.id.list_fragment, new NotesList())
                            .commit();

                } else {
                    adapter.addData(note);
                    Save.save(getActivity());
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                }

            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.note_create_fragment, container, false);
    }
}
