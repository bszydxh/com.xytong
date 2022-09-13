package com.xytong.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xytong.model.entity.ReData;

import java.util.List;

@Dao
public interface ReDataDao {
    @Insert
    void insertRe(ReData... words);

    @Update
    int updateRe(ReData... words);

    @Delete
    void deleteRe(ReData... words);

    @Query("SELECT * FROM re_list")
    List<ReData> getAllRe();
}