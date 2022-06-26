package com.xytong.image;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xytong.R;

public class ImageGetter {
    static public void setAvatarViewBitmap(ImageView view, String url) {
//        new Thread(() -> {
//
//            FutureTarget<Bitmap> futureTarget =
//                    Glide.with(view.getContext())
//                            .asBitmap()
//                            .load(url)
//                            .placeholder(R.mipmap.default_user_avatar)
//                            .submit(200, 200);
//            view.post(() -> {
//                try {
//                    view.setImageBitmap(futureTarget.get());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        }).start();
        Glide.with(view.getContext())
                .load(url)
                .placeholder(R.mipmap.default_user_avatar)
                .into(view);
    }
}

