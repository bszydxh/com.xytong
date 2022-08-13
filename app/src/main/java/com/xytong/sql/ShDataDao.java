package com.xytong.sql;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xytong.data.ShData;

import java.util.List;
@Dao
public interface ShDataDao {
    @Insert
    void  insertSh(ShData... words);
    @Update
    int updateSh(ShData... words);
    @Delete
    void deleteSh(ShData...words);

    @Query("SELECT * FROM sh_list")
    List<ShData> getAllSh();
}