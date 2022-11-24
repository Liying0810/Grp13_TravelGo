package com.example.group13.model;

public class Reservation {
     String key, table_id, date, time, compare_time;

    public Reservation(){

    }
    public Reservation(String table_id, String date, String time, String compare_time){
this.table_id=table_id;
this.date=date;
this.time=time;
this.compare_time=compare_time;
    }

    public String getCompare_time() {
        return compare_time;
    }

    public void setCompare_time(String compare_time) {
        this.compare_time = compare_time;
    }

    public String getTable_id(){return table_id;}
    public void setTable_id(String table_id){this.table_id=table_id;}

    public String getKey(){return key;}
    public void setKey(String key){this.key=key;}

    public String getDate(){return date;}
    public void setDate(String date){this.date=date;}

    public String getTime(){return time;}
    public void setTime(String time){this.time=date;}
}


