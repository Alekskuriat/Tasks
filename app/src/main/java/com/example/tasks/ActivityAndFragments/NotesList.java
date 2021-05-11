package com.example.tasks.ActivityAndFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tasks.NoteClickListener;
import com.example.tasks.notePackage.NotesAdapter;
import com.example.tasks.PublisherHolder;
import com.example.tasks.R;
import com.example.tasks.notePackage.Note;
import com.example.tasks.notePackage.NotesRepository;

public class NotesList extends Fragment {

    private NoteClickListener noteClickListener;
    private RecyclerView notesListRecycler;
    private NotesAdapter adapter;
    private boolean isLandscape = false;
    private NotesRepository notesRepository;

    public NotesList() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        notesRepository = activity.getNotesRepository();
        if (context instanceof NoteClickListener) {
            noteClickListener = (NoteClickListener) context;
        }
    }

    @Override
    public void onDetach() {
        noteClickListener = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_view_list, container, false);
    }


    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        requireActivity().getMenuInflater().inflate(R.menu.menu_list_context, menu);
    }


    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.action_open) {
            adapter.openNote(adapter.getItemAt(adapter.getLongClickedPosition()));
            return true;
        }

        if (item.getItemId() == R.id.action_delete) {
            adapter.delete(adapter.getItemAt(adapter.getLongClickedPosition()));
            return true;
        }

        if (item.getItemId() == R.id.action_edit) {
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, NoteEditFragment.newInstance(adapter.getItemAt(adapter.getLongClickedPosition())))
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return super.onContextItemSelected(item);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new NotesAdapter(this);

        adapter.setClickListener(new NotesAdapter.NoteClicked() {
            @Override
            public void onNoteClicked(Note note) {
                openNoteDetail(note);
            }
        });

        notesListRecycler = view.findViewById(R.id.notes_list_recycler);

        RecyclerView.LayoutManager lm = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);

        notesListRecycler.setLayoutManager(lm);

        notesListRecycler.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        DefaultItemAnimator itemAnimator = new DefaultItemAnimator();

        notesListRecycler.setItemAnimator(itemAnimator);

    }

    private void openNoteDetail(Note note) {
        if (getActivity() instanceof PublisherHolder) {
            PublisherHolder holder = (PublisherHolder) getActivity();
            holder.getPublisher().notify(note);
        }

        if (noteClickListener != null) {
            noteClickListener.onNoteClicked(note);
        }
    }
}