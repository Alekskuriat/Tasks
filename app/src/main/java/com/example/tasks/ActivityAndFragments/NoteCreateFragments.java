package com.example.tasks.ActivityAndFragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tasks.notePackage.NotesAdapter;
import com.example.tasks.R;
import com.example.tasks.notePackage.Note;
import com.example.tasks.notePackage.NotesRepository;

import java.util.Calendar;

public class NoteCreateFragments extends Fragment {


    private boolean isLandscape = false;
    private EditText nameNote;
    private EditText contentNote;
    private TextView numberNote;
    private Button createNote;
    private NotesAdapter adapter;
    private TextView noteDatePlan;
    private NotesRepository notesRepository;
    private DatePicker datePicker;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        isLandscape = getResources().getBoolean(R.bool.isLandscape);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        notesRepository = activity.getNotesRepository();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new NotesAdapter(this);
        nameNote = view.findViewById(R.id.name_create);
        contentNote = view.findViewById(R.id.note_details_create);
        numberNote = view.findViewById(R.id.title_create);
        createNote = view.findViewById(R.id.btn_create_note);
        noteDatePlan = view.findViewById(R.id.note_date_plan);
        datePicker = view.findViewById(R.id.datePicker);
        Calendar today = Calendar.getInstance();

        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateChanged(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                        noteDatePlan.setText(year + "." + (monthOfYear + 1) + "." + dayOfMonth);
                    }
                });

        numberNote.setText(getString(R.string.note_number).concat(String.valueOf(Integer.parseInt(notesRepository.getLastNumberNote()) + 1)));
        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = new Note(nameNote.getText().toString(), contentNote.getText().toString(), Integer.parseInt(notesRepository.getLastNumberNote()), noteDatePlan.getText().toString());
                if (isLandscape) {
                    adapter.addData(note);

                    if (getActivity() != null)
                        Save.save(getActivity());

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.details_fragment, DetailsNote.newInstance(note))
                            .replace(R.id.list_fragment, new NotesList())
                            .commit();

                } else {
                    adapter.addData(note);

                    if (getActivity() != null)
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
