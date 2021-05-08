package com.example.tasks.notePackage;




import android.os.Parcel;
import android.os.Parcelable;

import com.example.tasks.ActivityAndFragments.NotesList;
import com.example.tasks.TimeAndDate.TimeAndDate;


public class Note implements Parcelable {

    private String name;
    private String content;
    private String dateTime;
    //private static int number = 0;
    private int serialNumber;

    public Note(String name, String content, int number) {
        this.name = name;
        this.content = content;
        serialNumber = ++number;
        dateTime = new TimeAndDate().getTimeAndDate();
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

    public void setContent(String content) {
        this.content = content;
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

}
