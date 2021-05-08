package com.example.tasks.ActivityAndFragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.tasks.NoteClickListener;
import com.example.tasks.Publisher;
import com.example.tasks.PublisherHolder;
import com.example.tasks.R;
import com.example.tasks.notePackage.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NoteClickListener, PublisherHolder {

    private Publisher publisher = new Publisher();
    private boolean isLandscape = false;
    private FloatingActionButton floatingActionButton;

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
        initNavigationDrawer();

        floatingActionButton = findViewById(R.id.floating_action_button);

        isLandscape = getResources().getBoolean(R.bool.isLandscape);

        if (isLandscape) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.list_fragment);
            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.list_fragment, new NotesList())
                        .commit();
            }
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment = fragmentManager.findFragmentById(R.id.container);
            if (fragment == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new NotesList())
                        .commit();
            }
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                if (isLandscape) {
                    fragmentManager.beginTransaction()
                            .replace(R.id.details_fragment, new NoteCreateFragments())
                            .replace(R.id.list_fragment, new NotesList())
                            .addToBackStack("1")
                            .commit();
                } else {
                    fragmentManager.beginTransaction()
                            .replace(R.id.container, new NoteCreateFragments())
                            .addToBackStack("1")
                            .commit();
                }
            }
        });
    }

    private void initNavigationDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        @SuppressLint("ResourceType")
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.nav_home){
                    Toast.makeText(MainActivity.this, "Кнопка", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.nav_gallery){
                    Toast.makeText(MainActivity.this, "Кнопка", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.nav_slideshow){
                    Toast.makeText(MainActivity.this, "Кнопка", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (isLandscape) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fr = fragmentManager.findFragmentById(R.id.details_fragment);
            Note note = fr.getArguments().getParcelable("ARG_NOTE");

            if (item.getItemId() == R.id.edit_btn_menu) {
                editNote();
            }

            if (item.getItemId() == R.id.delete_btn_menu) {
                int positionPreviousNote = NotesList.notes.getNotes().indexOf(note);
                NotesList.notes.deleteNote(note);
                if (NotesList.notes.getNote(1) != null && positionPreviousNote > 0) {
                    Note notePrevious = NotesList.notes.getNote(positionPreviousNote);
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
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fr = fragmentManager.findFragmentById(R.id.container);

            if (item.getItemId() == R.id.edit_btn_menu) {
                fragmentManager.beginTransaction()
                        .replace(R.id.container, NoteEditFragment.newInstance(fr.getArguments().getParcelable("ARG_NOTE")))
                        .addToBackStack(null)
                        .commit();
            }
            if (item.getItemId() == R.id.delete_btn_menu) {
                NotesList.notes.deleteNote(fr.getArguments().getParcelable("ARG_NOTE"));
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new NotesList())
                        .commit();
            }
        }

        Save.save(getApplicationContext());
        return super.onOptionsItemSelected(item);

    }

    public void editNote() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fr = fragmentManager.findFragmentById(R.id.details_fragment);
        Note note = fr.getArguments().getParcelable("ARG_NOTE");
        fragmentManager.beginTransaction()
                .replace(R.id.details_fragment, NoteEditFragment.newInstance(note))
                .addToBackStack(null)
                .commit();
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