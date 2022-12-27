package com.xytong.utils;


import android.content.Context;
import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * Glide 将下载到的图片转成灰色图片
 * <p>
 * Created by bayin on 2017/2/16.
 */

public class GreyPicTransformer extends BitmapTransformation {
    private static final String ID = "com.xytong.utils.GreyPicTransformer";
    private static final byte[] ID_BYTES = ID.getBytes(StandardCharsets.UTF_8);

    public GreyPicTransformer(Context context) {
        super();
    }

    @Override
    protected Bitmap transform(@NotNull BitmapPool pool, @NotNull Bitmap toTransform, int outWidth, int outHeight) {
        //这里就是上面我们写的工具类方法
        return ImageUtils.convertGreyImg(toTransform);
    }
    @Override
    public boolean equals(Object o) {
        return o instanceof GreyPicTransformer;
    }
    @Override
    public int hashCode() {
        return ID.hashCode();
    }

    @Override
    public void updateDiskCacheKey(@NotNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
    }
}

