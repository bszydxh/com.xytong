package com.xytong.sqlite;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;

public class MySQL {
    SQLiteDatabase db;
    File db_file;
    public MySQL(String address) {
        db_file = new File(address);
        if(db_file.exists())
        {
            Log.d("SQLite", "ok");
        }
        {
            Log.e("SQLite", "error");
        }
    }
    public void setup() {

    }

}
