package com.xytong.dao;

import androidx.room.*;
import com.xytong.model.po.ForumPO;

import java.util.List;

@Dao
public interface ForumDataDao {
    @Insert
    void insertForum(ForumPO... words);

    @Update
    int updateForum(ForumPO... words);

    @Delete
    void deleteForum(ForumPO... words);

    @Query("SELECT * FROM forum")
    List<ForumPO> getAllForum();
}
