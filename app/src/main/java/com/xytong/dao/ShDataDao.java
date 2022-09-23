package com.xytong.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xytong.model.vo.ShVO;

import java.util.List;

@Dao
public interface ShDataDao {
    @Insert
    void insertSh(ShVO... words);

    @Update
    int updateSh(ShVO... words);

    @Delete
    void deleteSh(ShVO... words);

    @Query("SELECT * FROM sh_list")
    List<ShVO> getAllSh();
}