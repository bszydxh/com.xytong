package com.xytong.sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xytong.data.ForumData;
import com.xytong.data.ShData;

import java.io.File;

public class MySQL {
    SQLiteDatabase db;
    File db_file;
    Cursor forum_cursor;
    String sql_create_forum_table = "create table forum_list (id integer primary key autoincrement,user_name text,user_avatar text,title text,text text,likes text,comments text,forwarding text);";
    String sql_create_secondhand_table = "create table secondhand_list (id integer primary key autoincrement,user_name text,user_avatar text,title text,text text);";

    public MySQL(String address) {
        db_file = new File(address);
        if (db_file.exists()) {
            Log.d("SQLite", "ok");
        } else {
            Log.e("SQLite", "not found db,now setup");
            try {
                db_file.createNewFile();
            } catch (Exception e) {
                Log.e("SQLite", "create file error");
            }
        }
        db = SQLiteDatabase.openOrCreateDatabase(db_file, null);
        try {
            forum_cursor = db.query("forum_list", null, null, null, null, null, null, null);
        } catch (Exception e) {
            Log.e("SQLite", "not found needy db,now create");
            db.execSQL(sql_create_forum_table);
            forum_cursor = db.query("forum_list", null, null, null, null, null, null, null);
            forum_cursor.moveToFirst();
            e.printStackTrace();
        }
        forum_cursor.moveToFirst();
    }

    public ShData read_secondhand_data() {
        /*读取后自动下一条*/
        ShData ShData = new ShData();
        Log.e("read_forum_data", forum_cursor.getPosition() + "");
        try {
            String str = forum_cursor.getString(3);
            ShData.setUserName(forum_cursor.getString(1));
            ShData.setUserAvatarUrl(forum_cursor.getString(2));
            ShData.setTitle(forum_cursor.getString(3).trim());
            ShData.setText(forum_cursor.getString(4));
        } catch (Exception e) {
            e.printStackTrace();
        }
        forum_cursor.moveToNext();
        return ShData;
    }

    public ForumData read_forum_data() {
        /*读取后自动下一条*/
        ForumData forumData = new ForumData();
        Log.e("read_forum_data", forum_cursor.getPosition() + "");
        try {
            String str = forum_cursor.getString(3);
            forumData.setUserName(forum_cursor.getString(1));
            forumData.setUserAvatarUrl(forum_cursor.getString(2));
            forumData.setTitle(forum_cursor.getString(3).trim());
            forumData.setText(forum_cursor.getString(4));
            forumData.setLikes(Integer.valueOf(forum_cursor.getString(5)));
            forumData.setComments(Integer.valueOf(forum_cursor.getString(6)));
            forumData.setForwarding(Integer.valueOf(forum_cursor.getString(7)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        forum_cursor.moveToNext();
        return forumData;
    }
}
