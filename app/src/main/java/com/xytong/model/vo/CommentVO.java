package com.xytong.model.vo;

import android.util.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xytong.view.LikeThump;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommentVO extends CardVO implements Serializable, LikeThump {
    Integer floor = -1;
    Integer likes = 0;

    boolean liked = false;

    @Override
    @Deprecated
    @JsonIgnore
    public String getTitle() {
        Log.w(getClass().getName() + ".getTitle()", "Don`t use this method!");
        return "";
    }

    @Override
    @Deprecated
    @JsonIgnore
    public void setTitle(String title) {
        Log.w(getClass().getName() + ".setTitle()", "Don`t use this method!");
    }

    @Override
    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

}
