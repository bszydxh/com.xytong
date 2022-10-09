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

    public ForumVO() {
    }

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

    public Boolean getLiked() {
        return this.liked;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof ForumVO)) return false;
        final ForumVO other = (ForumVO) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$likes = this.getLikes();
        final Object other$likes = other.getLikes();
        if (this$likes == null ? other$likes != null : !this$likes.equals(other$likes)) return false;
        final Object this$comments = this.getComments();
        final Object other$comments = other.getComments();
        if (this$comments == null ? other$comments != null : !this$comments.equals(other$comments)) return false;
        final Object this$forwarding = this.getForwarding();
        final Object other$forwarding = other.getForwarding();
        if (this$forwarding == null ? other$forwarding != null : !this$forwarding.equals(other$forwarding))
            return false;
        final Object this$liked = this.getLiked();
        final Object other$liked = other.getLiked();
        if (this$liked == null ? other$liked != null : !this$liked.equals(other$liked)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ForumVO;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $likes = this.getLikes();
        result = result * PRIME + ($likes == null ? 43 : $likes.hashCode());
        final Object $comments = this.getComments();
        result = result * PRIME + ($comments == null ? 43 : $comments.hashCode());
        final Object $forwarding = this.getForwarding();
        result = result * PRIME + ($forwarding == null ? 43 : $forwarding.hashCode());
        final Object $liked = this.getLiked();
        result = result * PRIME + ($liked == null ? 43 : $liked.hashCode());
        return result;
    }

    public String toString() {
        return "ForumVO(likes=" + this.getLikes() + ", comments=" + this.getComments() + ", forwarding=" + this.getForwarding() + ", liked=" + this.getLiked() + ")";
    }
}
