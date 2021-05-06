package com.example.tasks.notePackage;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.tasks.ActivityAndFragments.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class NotesRepository implements Parcelable {

    ArrayList<Note> notes = new ArrayList<>();


    public NotesRepository() {

    }

    public void setNote(Note note) {
        notes.add(note);
    }

    public void editNote(Note note){
        for (int i = 0; i < notes.size(); i++) {
            if(note.getSerialNumber().equals(notes.get(i).getSerialNumber())){
                notes.set(i , note);
            }
        }

    }

    public Note getNote(int index){
        return notes.get(index - 1);
    }

    public void deleteNote(Note note) {
        if (notes.size() > 0){
            notes.remove(note);
        }

    }

    public String getLastNumberNote(){
        if (notes.size() > 0) {
            return notes.get(notes.size() - 1).getSerialNumber();
        } else return String.valueOf(0);
    }


    public int getSize() {
        return notes.size();
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
    public void setNotes(ArrayList<Note> list) {
        notes = list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(notes);
    }

    public Note getNoteforSerialNumber(int number) {
        for (int i = 0; i < notes.size(); i++) {
            if(Integer.parseInt(notes.get(i).getSerialNumber()) == number){
                return notes.get(i+1);
            }
        }
        return notes.get(0);
    }
}
