package com.xytong.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.xytong.model.vo.ForumVO;
import com.xytong.model.vo.ReVO;
import com.xytong.model.vo.ShVO;

@Database(entities = {ForumVO.class, ReVO.class, ShVO.class}, version = 2)

public abstract class CoreDataBase extends RoomDatabase {//单例模式交由sqliteGetter实现

    public abstract ForumDataDao getForumDataDao();

    public abstract ReDataDao getReDataDao();

    public abstract ShDataDao getShDataDao();
}