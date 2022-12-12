package com.xytong.dao;

import androidx.room.*;
import com.xytong.model.po.RePO;

import java.util.List;

@Dao
public interface ReDataDao {
    @Insert
    void insertRe(RePO... words);

    @Update
    int updateRe(RePO... words);

    @Delete
    void deleteRe(RePO... words);

    @Query("SELECT * FROM re")
    List<RePO> getAllRe();
}