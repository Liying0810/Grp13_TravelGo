package com.example.group13.model;

public class GetTimeAndDate {
    private String date;
    private  String time;

    public GetTimeAndDate(String date, String name){
        this.date=date;
                this.time=time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
