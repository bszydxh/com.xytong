package com.xytong.image;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class ImageDownloader {
    static public void setBitmap(ImageView view, String url) {
        new Thread(() -> {
            Bitmap bitmap = ImageCache.getBitmap(url);
            view.post(() -> view.setImageBitmap(bitmap));
        }).start();
    }
}

