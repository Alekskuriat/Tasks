package com.example.tasks.FireStore;

import androidx.annotation.NonNull;

import com.example.tasks.notePackage.Note;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FireStoreRepository {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private static final String NOTES = "notes";
    private static final String CONTENT = "content";
    private static final String NAME = "name";
    private static final String SERIAL = "serialNumber";
    private static final String DATE_PLAN = "datePlan";
    private static final String DATE_TIME = "dateTime";

    public void getNotes(Callback<List<Note>> callback) {

        firestore.collection(NOTES)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {

                            List<Note> tmp = new ArrayList<>();
                            List<DocumentSnapshot> docs = task.getResult().getDocuments();

                            for (DocumentSnapshot doc : docs) {

                                String id = doc.getId();
                                String content = doc.getString(CONTENT);
                                String name = doc.getString(NAME);
                                int serialNumber = Math.toIntExact(doc.getLong(SERIAL));
                                String dateTime = doc.getString(DATE_TIME);
                                String datePlan = doc.getString(DATE_PLAN);

                                tmp.add(new Note(name, content, serialNumber, datePlan, dateTime));
                            }

                            callback.onSuccess(tmp);

                        } else {
                            callback.onError(task.getException());
                        }

                    }
                });

    }
}
