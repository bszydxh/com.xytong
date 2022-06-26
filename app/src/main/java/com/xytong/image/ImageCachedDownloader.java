package com.xytong.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ImageCachedDownloader {//该类被废弃，使用谷歌推荐的图片库Glide
    @Deprecated
    public static Bitmap getBitmap(String url_str) {
        Bitmap bitmap = null;
        if (true)//数据库查询有无图片bitmap资源
        {

        } else {
            //启动下载器
        }
        try {
            URL url = new URL(url_str);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            int contentLength = httpURLConnection.getContentLength();
            urlConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, contentLength);//带缓存区的输入流，用于提高性能
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);//调用工厂方法，创建一个合理的bitmap类
            bufferedInputStream.close();
            inputStream.close();
            Log.i("ImageCache", url_str + " get ok");
        } catch (Exception e) {
            //e.printStackTrace();
            Log.e("ImageCache", url_str + " get error");
        }

        return bitmap;
    }
}
