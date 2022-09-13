package com.xytong.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import com.xytong.view.LikeThump;

import java.io.Serializable;

@Entity(tableName = "forum_list")
public class ForumData extends CardData implements Serializable, LikeThump {
    @ColumnInfo
    private Integer likes = 0;
    @ColumnInfo
    private Integer comments = 0;
    @ColumnInfo
    private Integer forwarding = 0;
    ////////////////////////////////////////////////////////
    @Ignore
    private Boolean liked = false;

    public Integer getComments() {
        return comments;
    }

    public Integer getForwarding() {
        return forwarding;
    }

    @Override
    public Integer getLikes() {
        return likes;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public void setForwarding(Integer forwarding) {
        this.forwarding = forwarding;
    }

    @Override
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public boolean isLiked() {
        return liked;
    }

    @Override
    public void setLiked(Boolean liked) {
        this.liked = liked;
    }
}
