package com.example.tasks.notePackage;


import android.os.Parcel;
import android.os.Parcelable;

import com.example.tasks.ActivityAndFragments.NotesList;
import com.example.tasks.TimeAndDate.TimeAndDate;


public class Note implements Parcelable {

    private String id;
    private String name;
    private String content;
    private final String dateTime;
    private final int serialNumber;
    private String datePlan;

    public Note(String name, String content, int number, String datePlan) {
        this.name = name;
        this.content = content;
        this.datePlan = datePlan;
        serialNumber = ++number;
        dateTime = new TimeAndDate().getTimeAndDate();
    }


    public Note(String name, String content, int number, String datePlan, String dateTime) {
        this.name = name;
        this.content = content;
        this.datePlan = datePlan;
        serialNumber = number;
        this.dateTime = dateTime;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerialNumber() {
        return String.valueOf(serialNumber);
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getContent() {
        return content;
    }

    protected Note(Parcel in) {
        name = in.readString();
        content = in.readString();
        serialNumber = Integer.parseInt(in.readString());
        dateTime = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(content);
        dest.writeString(String.valueOf(serialNumber));
        dest.writeString(dateTime);
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getDatePlan() {
        return datePlan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
