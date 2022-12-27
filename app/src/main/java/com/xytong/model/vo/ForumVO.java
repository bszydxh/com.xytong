package com.xytong.model.vo;

import com.xytong.view.LikeThump;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class ForumVO extends CardVO implements Serializable, LikeThump {
    private Integer likes;
    private Integer comments;
    private Integer forwarding;
    ////////////////////////////////////////////////////////
    private Boolean liked = false;

    public Boolean getLiked() {
        return this.liked;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ForumVO;
    }

    @Override
    public boolean isLiked() {
        return liked;
    }
}
