package com.xytong.model.entity;

import android.util.Log;

import com.xytong.view.LikeThump;

import java.io.Serializable;

public class CommentData extends CardData implements Serializable, LikeThump {
    Integer floor = -1;
    Integer likes = 0;
    boolean liked = false;

    @Override
    @Deprecated
    public String getTitle() {
        Log.w(getClass().getName() + ".getTitle()", "Don`t use this method!");
        return "";
    }
    @Override
    @Deprecated
    public void setTitle(String title) {
        Log.w(getClass().getName() + ".setTitle()", "Don`t use this method!");
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    @Override
    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    @Override
    public boolean isLiked() {
        return liked;
    }

    @Override
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public Integer getLikes() {
        return likes;
    }
}
