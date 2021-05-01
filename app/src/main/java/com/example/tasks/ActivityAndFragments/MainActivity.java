package com.example.tasks.ActivityAndFragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.tasks.OnNoteClicked;
import com.example.tasks.Publisher;
import com.example.tasks.PublisherHolder;
import com.example.tasks.R;
import com.example.tasks.notePackage.Note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity implements OnNoteClicked, PublisherHolder {

    private Publisher publisher = new Publisher();

    private boolean isLandscape = false;

    Button btnAddNote;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLandscape = getResources().getBoolean(R.bool.isLandscape);

        if (!isLandscape) {
            FragmentManager fragmentManager = getSupportFragmentManager();

            Fragment fragment = fragmentManager.findFragmentById(R.id.container);

            if (fragment == null) {

                fragmentManager.beginTransaction()
                        .replace(R.id.container, new NotesList())
                        .commit();
            }
        }

        btnAddNote = findViewById(R.id.btn_add_note);
        btnAddNote.setVisibility(View.VISIBLE);
        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    public void onNoteClicked(Note note) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (isLandscape) {
            fragmentManager.beginTransaction()
                    .replace(R.id.details_fragment, DetailsNote.newInstance(note))
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, DetailsNote.newInstance(note))
                    .addToBackStack(null)
                    .commit();

        }
    }
}