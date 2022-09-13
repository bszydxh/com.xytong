package com.xytong.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xytong.R;

public class ImageGetter {
    static public void setAvatarViewBitmap(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.mipmap.default_user_avatar)
                .into(view);
    }
}

