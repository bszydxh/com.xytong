package com.xytong.data;

import com.xytong.ui.LikeThump;

import java.io.Serializable;

public class ForumData extends CardData implements Serializable, LikeThump {
    private Integer likes = 0;
    private Integer comments = 0;
    private Integer forwarding = 0;
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
