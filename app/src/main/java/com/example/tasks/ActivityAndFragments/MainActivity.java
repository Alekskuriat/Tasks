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

import com.example.tasks.FireStore.Callback;
import com.example.tasks.FireStore.FireStoreRepository;
import com.example.tasks.NoteClickListener;
import com.example.tasks.Publisher;
import com.example.tasks.PublisherHolder;
import com.example.tasks.R;
import com.example.tasks.notePackage.Note;
import com.example.tasks.notePackage.NotesRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteClickListener, PublisherHolder {

    private Publisher publisher = new Publisher();
    private boolean isLandscape = false;
    private FloatingActionButton floatingActionButton;
    private FireStoreRepository repository = new FireStoreRepository();
    private NotesRepository notesRepository = new NotesRepository();
    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    private static final String NOTES = "notes";
    private static final String NAME = "name";
    private static final String CONTENT = "content";
    private static final String SERIAL = "serialNumber";
    private static final String DATE_PLAN = "datePlan";
    private static final String DATE_TIME = "dateTime";


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//loadDate();
        repository.getNotes(new Callback<List<Note>>() {
            public void onSuccess(List<Note> value){
                notesRepository.setNotes(value);
            }

            public void onError(Throwable error){

            }
        });

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


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
                if (item.getItemId() == R.id.nav_home) {
                    Toast.makeText(MainActivity.this, "Кнопка", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.nav_gallery) {
                    Toast.makeText(MainActivity.this, "Кнопка", Toast.LENGTH_SHORT).show();
                }
                if (item.getItemId() == R.id.nav_slideshow) {
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
    protected void onPause() {
        Save.save(this);
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
        if (notesList == null) {
            notesList = new ArrayList<>();
        }
        notesRepository.setNotes(notesList);
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

    public NotesRepository getNotesRepository() {
        return notesRepository;
    }
}