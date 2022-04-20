package com.xytong.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class ImageCache {
    public static Bitmap getBitmap(String url_str) {
        Bitmap bitmap = null;
            Log.e("ImageCache","get ok");
        try {
            URL url = new URL(url_str);
            URLConnection connection = url.openConnection();
            HttpURLConnection http = (HttpURLConnection) connection;
            int length = http.getContentLength();
            connection.connect();
            InputStream inputStream = http.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream,length);
            bitmap= BitmapFactory.decodeStream(bufferedInputStream);
            bufferedInputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ImageCache","get error");
        }
        if (true)//数据库查询有无图片bitmap资源
        {

        } else {
            //启动下载器
        }
        return bitmap;
    }
}
