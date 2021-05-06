package com.example.tasks.ActivityAndFragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.NavController;

import com.example.tasks.OnNoteClicked;
import com.example.tasks.Publisher;
import com.example.tasks.PublisherHolder;
import com.example.tasks.R;
import com.example.tasks.notePackage.Note;
import com.example.tasks.notePackage.NotesRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnNoteClicked, PublisherHolder {

    private Publisher publisher = new Publisher();
    private boolean isLandscape = false;
    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadDate();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Notes");


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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_menu, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (isLandscape) {
            if (item.getItemId() == R.id.add_btn_menu) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.details_fragment, new NoteCreateFragments())
                        .addToBackStack("1")
                        .commit();
                return true;
            }
            if (item.getItemId() == R.id.edit_btn_menu) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fr = fragmentManager.findFragmentById(R.id.container);
                fragmentManager.beginTransaction()
                        .add(R.id.details_fragment, NoteEditFragment.newInstance(fr.getArguments().getParcelable("ARG_NOTE")))
                        .addToBackStack(null)
                        .commit();
            }

        } else {

            if (item.getItemId() == R.id.add_btn_menu) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new NoteCreateFragments())
                        .addToBackStack("1")
                        .commit();
                return true;
            }
            if (item.getItemId() == R.id.edit_btn_menu) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fr = fragmentManager.findFragmentById(R.id.container);
                fragmentManager.beginTransaction()
                        .replace(R.id.container, NoteEditFragment.newInstance(fr.getArguments().getParcelable("ARG_NOTE")))
                        .addToBackStack(null)
                        .commit();
            }
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPause() {
        Save.save(getApplicationContext());
        super.onPause();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    public void loadDate() {
        SharedPreferences sharedPreferences = getSharedPreferences("SP", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("notes list", null);
        Type type = new TypeToken<ArrayList<Note>>() {
        }.getType();
        ArrayList<Note> notesList = new ArrayList<>();
        notesList = gson.fromJson(json, type);
        NotesList.notes.setNotes(notesList);
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
                    .replace(R.id.details_fragment, DetailsNote.newInstance(note), "details")
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.container, DetailsNote.newInstance(note), "details")
                    .addToBackStack(null)
                    .commit();

        }
    }
}