package com.xytong.sql;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.xytong.data.ForumData;
import com.xytong.data.ReData;
import com.xytong.data.ShData;

@Database(entities = {ForumData.class, ReData.class, ShData.class}, version = 2)

public abstract class CoreDataBase extends RoomDatabase {//单例模式交由mysql实现

    public abstract ForumDataDao getForumDataDao();

    public abstract ReDataDao getReDataDao();

    public abstract ShDataDao getShDataDao();
}