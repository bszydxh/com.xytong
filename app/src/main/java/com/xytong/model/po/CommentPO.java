package com.xytong.model.po;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "comment")
public class CommentPO {
    @PrimaryKey(autoGenerate = true)
    public Long id;
}
