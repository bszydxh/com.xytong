package com.xytong.model.vo;

import androidx.room.Entity;
import androidx.room.Ignore;
import com.xytong.view.LikeThump;

import java.io.Serializable;

@Entity(tableName = "forum_list")
public class ForumVO extends CardVO implements Serializable, LikeThump {
    private Integer likes = 0;
    private Integer comments = 0;
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
