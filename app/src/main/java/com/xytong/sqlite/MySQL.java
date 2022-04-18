package com.xytong.sqlite;

import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import java.io.File;

public class MySQL {
    SQLiteDatabase db;
    @NonNull
    File db_file;
    public MySQL(File db_file)
    {
        this.db_file = db_file;
    }
    public void read_setup()
    {
        db =SQLiteDatabase.openDatabase(db_file,null);
    }

}
