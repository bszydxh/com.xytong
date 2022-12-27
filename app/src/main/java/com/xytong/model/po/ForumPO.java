package com.xytong.model.po;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "forum")
public class ForumPO {
    @PrimaryKey(autoGenerate = true)
    public Long id;
}
