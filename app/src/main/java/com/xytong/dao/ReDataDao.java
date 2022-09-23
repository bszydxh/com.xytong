package com.xytong.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xytong.model.vo.ReVO;

import java.util.List;

@Dao
public interface ReDataDao {
    @Insert
    void insertRe(ReVO... words);

    @Update
    int updateRe(ReVO... words);

    @Delete
    void deleteRe(ReVO... words);

    @Query("SELECT * FROM re_list")
    List<ReVO> getAllRe();
}