package com.xytong.model.vo;

import android.util.Log;
import com.xytong.view.LikeThump;

import java.io.Serializable;

public class CommentVO extends CardVO implements Serializable, LikeThump {
    Integer floor = -1;
    Integer likes = 0;
    boolean liked = false;

    public CommentVO() {
    }

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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof CommentVO)) return false;
        final CommentVO other = (CommentVO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$floor = this.getFloor();
        final Object other$floor = other.getFloor();
        if (this$floor == null ? other$floor != null : !this$floor.equals(other$floor)) return false;
        final Object this$likes = this.getLikes();
        final Object other$likes = other.getLikes();
        if (this$likes == null ? other$likes != null : !this$likes.equals(other$likes)) return false;
        if (this.isLiked() != other.isLiked()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof CommentVO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $floor = this.getFloor();
        result = result * PRIME + ($floor == null ? 43 : $floor.hashCode());
        final Object $likes = this.getLikes();
        result = result * PRIME + ($likes == null ? 43 : $likes.hashCode());
        result = result * PRIME + (this.isLiked() ? 79 : 97);
        return result;
    }

    public String toString() {
        return "CommentVO(floor=" + this.getFloor() + ", likes=" + this.getLikes() + ", liked=" + this.isLiked() + ")";
    }
}
