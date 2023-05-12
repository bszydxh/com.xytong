package com.xytong.service;

import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.xytong.R;
import com.xytong.utils.GreyPicTransformer;

public class ImageController
{
    static public void setAvatarViewBitmap(ImageView view, String url) {
        RequestOptions requestOptions = RequestOptions.bitmapTransform(new GreyPicTransformer(view.getContext()));
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.mipmap.default_user_avatar)
                .override(view.getWidth(), view.getHeight())
//                .apply(requestOptions)
                .into(view);
    }

    static public void setImageBitmap(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.mipmap.pic)
                .override(view.getWidth(), view.getHeight())
//                .apply(requestOptions)
                .into(view);
    }
}
