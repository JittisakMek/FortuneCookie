package com.egci428.fortunecookie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CommentsDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String [] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_COMMENT, MySQLiteHelper.COLUMN_DATE}; //_id & Comment

    public CommentsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase(); //NEED! we will read data from database and also write sth on it (database connection)
    }
    public void close(){
        dbHelper.close();
    }

    public void createComment (String count,String date){
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_COMMENT, count);
        values.put(MySQLiteHelper.COLUMN_DATE, date);
        long insertId = database.insert(MySQLiteHelper.TABLE_COOKIE,null,values);
    }
    public void deleteComment (Comment comment){
        long id = comment.getId();
        System.out.println("Comment deleted with id: "+id);
        database.delete(MySQLiteHelper.TABLE_COOKIE, MySQLiteHelper.COLUMN_ID + " = "+id,null);
    }
    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COOKIE, allColumns, null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Comment comment = cursorToComment(cursor);
            comments.add(comment); //comments: list of the comments
            cursor.moveToNext();
        }
        cursor.close();
        return comments;
    }
    private Comment cursorToComment(Cursor cursor){
        Comment comment = new Comment(Integer.parseInt(cursor.getString(1)), cursor.getString(2));
        System.out.println("String = "+cursor.getString(1));
        System.out.println("String = "+cursor.getString(2));
        comment.setId(cursor.getLong(0));
        return  comment;
    }
}