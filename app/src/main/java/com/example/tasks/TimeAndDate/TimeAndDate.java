package com.example.tasks.TimeAndDate;

import android.annotation.SuppressLint;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeAndDate {

    private final String TIMEZONE;
    private String dateTime;

    @SuppressLint("SimpleDateFormat")
    public TimeAndDate() {
        TIMEZONE = "Asia/Novosibirsk";
        Date date = new Date();
        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        dateFormat.setTimeZone(timeZone);
        dateTime = dateFormat.format(date);
    }

    public String getTimeAndDate(){
        return dateTime;
    }
}
