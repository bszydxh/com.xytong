package com.xytong.dao;

import androidx.room.*;
import com.xytong.model.po.ShPO;

import java.util.List;

@Dao
public interface ShDataDao {
    @Insert
    void insertSh(ShPO... words);

    @Update
    int updateSh(ShPO... words);

    @Delete
    void deleteSh(ShPO... words);

    @Query("SELECT * FROM sh")
    List<ShPO> getAllSh();
}