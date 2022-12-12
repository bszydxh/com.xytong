package com.xytong.model.po;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sh")
public class ShPO {
    @PrimaryKey(autoGenerate = true)
    public Long id;
}
