package com.xytong.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xytong.data.ForumData;

import java.util.List;
@Dao
public interface ForumDataDao {
    @Insert
    void  insertForum(ForumData... words);
    @Update
    int updateForum(ForumData... words);
    @Delete
    void deleteForum(ForumData...words);

    @Query("SELECT * FROM forum_list")
    List<ForumData> getAllForum();
}
