package com.xytong.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.xytong.dao.ForumDataDao;
import com.xytong.dao.ReDataDao;
import com.xytong.dao.ShDataDao;
import com.xytong.model.entity.ForumData;
import com.xytong.model.entity.ReData;
import com.xytong.model.entity.ShData;

@Database(entities = {ForumData.class, ReData.class, ShData.class}, version = 2)

public abstract class CoreDataBase extends RoomDatabase {//单例模式交由sqliteGetter实现

    public abstract ForumDataDao getForumDataDao();

    public abstract ReDataDao getReDataDao();

    public abstract ShDataDao getShDataDao();
}