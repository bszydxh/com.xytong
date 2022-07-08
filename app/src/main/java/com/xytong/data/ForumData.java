package com.xytong.data;

import java.io.Serializable;

public class ForumData extends CardData implements Serializable {
    private Integer likes = 0;
    private Integer comments = 0;
    private Integer forwarding = 0;
    private boolean Liked = false;

    public void setLiked(boolean liked) {
        Liked = liked;
    }

    public boolean isLiked() {
        return Liked;
    }

    public Integer getComments() {
        return comments;
    }

    public Integer getForwarding() {
        return forwarding;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public void setForwarding(Integer forwarding) {
        this.forwarding = forwarding;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }


}
