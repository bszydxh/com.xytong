package com.xytong.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.xytong.model.po.ForumPO;
import com.xytong.model.po.RePO;
import com.xytong.model.po.ShPO;

@Database(entities = {ForumPO.class, RePO.class, ShPO.class}, version = 2)

public abstract class CoreDataBase extends RoomDatabase {//单例模式交由sqliteGetter实现

    public abstract ForumDataDao getForumDataDao();

    public abstract ReDataDao getReDataDao();

    public abstract ShDataDao getShDataDao();
}