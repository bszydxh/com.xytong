package com.xytong.model.po;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "re")
public class RePO {
    @PrimaryKey(autoGenerate = true)
    public Long id;
}
