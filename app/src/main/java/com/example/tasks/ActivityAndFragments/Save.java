package com.example.tasks.ActivityAndFragments;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class Save {

    static public void save(Context context) {
        MainActivity activity = (MainActivity) context;
        SharedPreferences sharedPreferences = context.getSharedPreferences("SP", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(activity.getNotesRepository().getNotes());
        editor.putString("notes list", json);
        editor.apply();

    }
}
