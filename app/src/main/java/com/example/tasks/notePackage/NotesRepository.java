package com.example.tasks.notePackage;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository implements Parcelable {

    ArrayList<Note> notes = new ArrayList<>();

    public NotesRepository() {
        Note.setNumber();
        notes.add(new Note("Первая заметка","Заметка номер 1"));
        notes.add(new Note("Втрорая заметка","Заметка номер 2Заметка номер 2Заметка номер 2Заметка номер 2Заметка номер 2Заметка номер 2Заметка номер 2"));
        notes.add(new Note("Третья заметка","Заметка номер 3"));
        notes.add(new Note("Четвертая заметка","Заметка номер 4"));
        notes.add(new Note("Пятая заметка","Заметка номер 5"));
        notes.add(new Note("Шестая заметка","Заметка номер 6"));
        notes.add(new Note("Седьмая заметка","Заметка номер 7"));
        notes.add(new Note("Восьмая заметка","Заметка номер 8"));
        notes.add(new Note("Девятая заметка","Заметка номер 9"));
        notes.add(new Note("Десятая заметка","Заметка номер 10"));

    }

    protected NotesRepository(Parcel in) {
        notes = in.createTypedArrayList(Note.CREATOR);
    }

    public static final Creator<NotesRepository> CREATOR = new Creator<NotesRepository>() {
        @Override
        public NotesRepository createFromParcel(Parcel in) {
            return new NotesRepository(in);
        }

        @Override
        public NotesRepository[] newArray(int size) {
            return new NotesRepository[size];
        }
    };

    public List<Note> getNotes() {
        return notes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(notes);
    }
}
