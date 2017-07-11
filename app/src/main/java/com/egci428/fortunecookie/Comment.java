package com.egci428.fortunecookie;

/**
 * Created by Mek on 16/11/2559.
 */
public class Comment {
    private long id;
    private int count;
    private String date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public String getDate(){return date;}

    public Comment(int x, String y){
        this.count = x;
        this.date = y;
    }
}
