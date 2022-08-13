package com.xytong.sql;


import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import io.reactivex.annotations.NonNull;

public class MySQL {
    volatile private static MySQL mySQL = null;//双检查锁
    private CoreDataBase coreDataBase;
    public static MySQL getInstance(Context context) throws RuntimeException {
        if (mySQL == null) {
            synchronized (MySQL.class) {
                if (mySQL == null) {//二次检查
                    mySQL = new MySQL();
                    File db_file = new File(context.getApplicationContext().getExternalFilesDir("").getAbsolutePath() + "/coreData.db");
                    if (!db_file.exists()) {//初始化sql文件
                        Log.e("SQLite", "not found db,now setup");
                        try {
                            InputStream is = context.getAssets().open("coreData.db");
                            File newFile = new File(context.getApplicationContext().getExternalFilesDir("").getAbsolutePath() + "/coreData.db");
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

                    } else {
                        Log.d("SQLite", "ok");
                    }
                    mySQL.coreDataBase = Room.databaseBuilder(context, CoreDataBase.class, "coreData")
                            .createFromFile(db_file) //new a database
                            .build();
                } else {
                    Log.d("SQLite", "had instance");
                }
            }
        } else {
            Log.d("SQLite", "had instance");
        }
        return mySQL;
    }
    @NonNull
    public CoreDataBase getCoreDataBase() {
        return coreDataBase;
    }
}
