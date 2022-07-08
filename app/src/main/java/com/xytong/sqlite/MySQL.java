package com.xytong.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.xytong.data.ForumData;
import com.xytong.data.ReData;
import com.xytong.data.ShData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MySQL {
    private SQLiteDatabase db;
    private File db_file;
    private Cursor forum_cursor;
    private Cursor sh_cursor;
    private Cursor re_cursor;

    private final String sql_create_forum_table = "CREATE TABLE 'forum_list' (\n" +
            "  'id' INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "  'user_name' TEXT,\n" +
            "  'user_avatar' TEXT,\n" +
            "  'title' TEXT,\n" +
            "  'text' TEXT,\n" +
            "  'likes' TEXT,\n" +
            "  'comments' TEXT,\n" +
            "  'forwarding' TEXT,\n" +
            "  'timestamp' integer\n" +
            ");";
    private final String sql_create_re_table = "CREATE TABLE 'run_errands_list' (\n" +
            "  'id' INTEGER NOT NULL,\n" +
            "  'user_name' TEXT,\n" +
            "  'user_avater' TEXT,\n" +
            "  'title' TEXT,\n" +
            "  'text' TEXT,\n" +
            "  'price' text,\n" +
            "  'timestamp' integer,\n" +
            "  PRIMARY KEY ('id')\n" +
            ");";
    private final String sql_create_sh_table = "CREATE TABLE 'secondhand_list' (\n" +
            "  'id' INTEGER NOT NULL,\n" +
            "  'user_name' TEXT,\n" +
            "  'user_avater' TEXT,\n" +
            "  'title' TEXT,\n" +
            "  'text' TEXT,\n" +
            "  'price' text,\n" +
            "  'timestamp' integer,\n" +
            "  PRIMARY KEY ('id')\n" +
            ");";

    private Cursor setup_cursor(Cursor cursor, String table, String sql) {
        //db在内部隐式传递
        try {
            cursor = db.query(table, null, null, null, null, null, null, null);
        } catch (Exception e) {
            Log.e("SQLite", "not found needy db,now create");
            db.execSQL(sql);
            cursor = db.query(table, null, null, null, null, null, null, null);
            cursor.moveToFirst();
            //e.printStackTrace();
        }
        cursor.moveToFirst();
        return cursor;
    }

    public MySQL(Context context) throws RuntimeException{
        db_file = new File(context.getApplicationContext().getExternalFilesDir("").getAbsolutePath() + "/mydb.db");
        if (db_file.exists()) {
            Log.d("SQLite", "ok");
        } else {
            Log.e("SQLite", "not found db,now setup");
            try {
                InputStream is  =  context.getAssets().open("mydb.db");
                File newFile = new File(context.getApplicationContext().getExternalFilesDir("").getAbsolutePath() + "/mydb.db");
                FileOutputStream fos = new FileOutputStream(newFile);
                int len = -1;
                byte[] buffer = new byte[1024];
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                is.close();
            } catch (Exception e) {
                Log.e("SQLite", "create file error");
                throw new RuntimeException("create file error");
            }
        }
        db = SQLiteDatabase.openOrCreateDatabase(db_file, null);
        forum_cursor = setup_cursor(forum_cursor, "forum_list", sql_create_forum_table);
        sh_cursor = setup_cursor(sh_cursor, "secondhand_list", sql_create_sh_table);
        re_cursor = setup_cursor(re_cursor, "run_errands_list", sql_create_re_table);
    }

    public ReData read_run_errands_data() {
        /*读取后自动下一条*/
        ReData reData = new ReData();
        try {
            reData.setUserName(re_cursor.getString(1).trim());
            reData.setUserAvatarUrl(re_cursor.getString(2).trim());
            reData.setTitle(re_cursor.getString(3).trim());
            reData.setText(re_cursor.getString(4));
            reData.setPrice(re_cursor.getString(5));
            reData.setTimestamp(re_cursor.getLong(6));
        } catch (Exception e) {
            Log.e("SQLite", "read run_errands data error");
            //e.printStackTrace();
        }
        try {
            re_cursor.moveToNext();
        } catch (Exception e) {
            Log.e("SQLite", "next run_errands data error");
        }
        return reData;
    }

    public ShData read_secondhand_data() {
        /*读取后自动下一条*/
        ShData shData = new ShData();
        try {
            shData.setUserName(sh_cursor.getString(1).trim());
            shData.setUserAvatarUrl(sh_cursor.getString(2).trim());
            shData.setTitle(sh_cursor.getString(3).trim());
            shData.setText(sh_cursor.getString(4));
            shData.setPrice(sh_cursor.getString(5));
            shData.setTimestamp(sh_cursor.getLong(6));
        } catch (Exception e) {
            Log.e("SQLite", "read secondhand data error");
            //e.printStackTrace();
        }
        try {
            sh_cursor.moveToNext();
        } catch (Exception e) {
            Log.e("SQLite", "next secondhand data error");
        }
        return shData;
    }

    public ForumData read_forum_data() {
        /*读取后自动下一条*/
        ForumData forumData = new ForumData();
        try {
            forumData.setUserName(forum_cursor.getString(1).trim());
            forumData.setUserAvatarUrl(forum_cursor.getString(2).trim());
            forumData.setTitle(forum_cursor.getString(3).trim());
            forumData.setText(forum_cursor.getString(4));
            forumData.setLikes(Integer.valueOf(forum_cursor.getString(5)));
            forumData.setComments(Integer.valueOf(forum_cursor.getString(6)));
            forumData.setForwarding(Integer.valueOf(forum_cursor.getString(7)));
            forumData.setTimestamp(forum_cursor.getLong(8));
        } catch (Exception e) {
            Log.e("SQLite", "read forum data error");
        }
        try {
            forum_cursor.moveToNext();
        } catch (Exception e) {
            Log.e("SQLite", "next forum data error");
        }

        return forumData;
    }
}
