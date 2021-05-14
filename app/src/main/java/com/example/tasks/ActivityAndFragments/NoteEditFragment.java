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

import com.example.tasks.R;
import com.example.tasks.notePackage.Note;
import com.example.tasks.notePackage.NotesRepository;

import java.util.Calendar;

public class NoteEditFragment extends Fragment {

    private static final String ARG_NOTE = "ARG_NOTE";
    private boolean isLandscape = false;
    private EditText nameNote;
    private EditText contentNote;
    private TextView numberNote;
    private Button createNote;
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

        int year, month, day;

        nameNote = view.findViewById(R.id.name_edit);
        contentNote = view.findViewById(R.id.note_details_edit);
        numberNote = view.findViewById(R.id.title_edit);
        createNote = view.findViewById(R.id.btn_create_note);
        noteDatePlan = view.findViewById(R.id.note_date_plan);
        datePicker = view.findViewById(R.id.datePicker);
        Calendar today = Calendar.getInstance();

        assert getArguments() != null;
        Note note = getArguments().getParcelable(ARG_NOTE);


        numberNote.setText(getString(R.string.note_number).concat(note.getSerialNumber()));
        nameNote.setText(note.getName());
        contentNote.setText(note.getContent());
        noteDatePlan.setText(note.getDatePlan());

        if (!noteDatePlan.getText().toString().equals("")) {
            String[] dateParce = noteDatePlan.getText().toString().split("\\.");
            year = Integer.parseInt(dateParce[0]);
            month = Integer.parseInt(dateParce[1]) - 1;
            day = Integer.parseInt(dateParce[2]);
        } else {
            year = today.get(Calendar.YEAR);
            month = today.get(Calendar.MONTH);
            day = today.get(Calendar.DAY_OF_MONTH);
        }

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                noteDatePlan.setText(year + "." + (monthOfYear + 1) + "." + dayOfMonth);
            }
        });


        createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note newNote = new Note(String.valueOf(nameNote.getText()), String.valueOf(contentNote.getText()), Integer.parseInt(note.getSerialNumber()) - 1, noteDatePlan.getText().toString());
                FragmentManager fragmentManager = getParentFragmentManager();
                if (isLandscape) {
                    notesRepository.editNote(newNote);
                    Toast.makeText(requireContext(), "Сохранено", Toast.LENGTH_SHORT).show();
                    fragmentManager.beginTransaction()
                            .replace(R.id.list_fragment, new NotesList())
                            .commit();
                } else {
                    notesRepository.editNote(newNote);
                    Toast.makeText(requireContext(), "Сохранено", Toast.LENGTH_SHORT).show();

                }
                if (getActivity() != null)
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



