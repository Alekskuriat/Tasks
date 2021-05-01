package com.example.tasks;

import com.example.tasks.notePackage.Note;

import java.util.ArrayList;
import java.util.List;

public class Publisher {

    private final List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notify(Note note) {
        for (Observer observer : observers) {
            observer.updateNote(note);
        }
    }
}
