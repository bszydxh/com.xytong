package com.xytong.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xytong.model.vo.ForumVO;

import java.util.List;

@Dao
public interface ForumDataDao {
    @Insert
    void insertForum(ForumVO... words);

    @Update
    int updateForum(ForumVO... words);

    @Delete
    void deleteForum(ForumVO... words);

    @Query("SELECT * FROM forum_list")
    List<ForumVO> getAllForum();
}
