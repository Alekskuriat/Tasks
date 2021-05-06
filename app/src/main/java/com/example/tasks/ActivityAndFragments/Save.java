package com.example.tasks.ActivityAndFragments;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Save {

    static public void save(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SP", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(NotesList.notes.getNotes());
        editor.putString("notes list", json);
        editor.apply();

    }
}
