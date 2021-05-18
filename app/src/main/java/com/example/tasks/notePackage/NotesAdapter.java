package com.example.tasks.notePackage;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tasks.ActivityAndFragments.MainActivity;
import com.example.tasks.ActivityAndFragments.NotesList;
import com.example.tasks.R;


public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private NoteClicked clickListener;
    private final Fragment fragment;
    private int longClickedPosition = -1;
    private NotesRepository notesRepository;


    public NotesAdapter(Fragment fragment) {
        this.fragment = fragment;

        MainActivity activity = (MainActivity) fragment.getActivity();
        if (activity != null) {
            notesRepository = activity.getNotesRepository();
        }
    }


    public void addData(Note note) {
        notesRepository.addNote(note);
        int position = notesRepository.getNotes().size() - 1;
        notifyItemInserted(position - 1);
    }

    public void delete(Note note) {
        int position = notesRepository.getNotes().indexOf(note);
        notesRepository.deleteNote(note);
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_note, parent, false);
        return new NotesViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        Note note = notesRepository.getNotes().get(position);
        holder.nameNote.setText(note.getName());
        holder.content.setText(note.getContent());
        holder.dateAndTime.setText(note.getDateTime());
        holder.numberNote.setText("№" + note.getSerialNumber());
        if (!note.getDatePlan().equals("")) {
            holder.datePlan.setText(note.getDatePlan());
        } else  {
            holder.datePlan.setVisibility(View.INVISIBLE);
            holder.datePlanString.setVisibility(View.INVISIBLE);
        }
        holder.btnDeleteNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(note);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notesRepository.getNotes().size();
    }

    public NoteClicked getClickListener() {
        return clickListener;
    }

    public Fragment getFragment(){
        return fragment;
    }


    public void setClickListener(NoteClicked clickListener) {
        this.clickListener = clickListener;
    }

    public int getLongClickedPosition() {
        return longClickedPosition;
    }

    public Note getItemAt(int longClickedPosition) {
        return notesRepository.getNotes().get(longClickedPosition);
    }

    public void openNote(Note note) {
        getClickListener().onNoteClicked(note);
    }

    public interface NoteClicked {
        void onNoteClicked(Note note);
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView nameNote;
        TextView numberNote;
        TextView content;
        TextView dateAndTime;
        TextView datePlan;
        TextView datePlanString;
        Button btnDeleteNote;

        public NotesViewHolder(@NonNull View itemView) {
            super(itemView);
            fragment.registerForContextMenu(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (getClickListener() != null) {
                        getClickListener().onNoteClicked(notesRepository.getNotes().get(getAdapterPosition()));

                    }
                }
            });


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemView.showContextMenu();
                    longClickedPosition = getAdapterPosition();
                    return true;
                }
            });

            nameNote = itemView.findViewById(R.id.name_view);
            numberNote = itemView.findViewById(R.id.numbers_view);
            content = itemView.findViewById(R.id.task_view);
            dateAndTime = itemView.findViewById(R.id.data_view);
            btnDeleteNote = itemView.findViewById(R.id.btn_remove_note);
            datePlan = itemView.findViewById(R.id.note_date_plan);
            datePlanString = itemView.findViewById(R.id.date_plan);

        }
    }


}
