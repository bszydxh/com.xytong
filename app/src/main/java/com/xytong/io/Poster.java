package com.xytong.io;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class Poster<T> implements Callable<T> {
    private HttpListener<T> httpListener;
    private String path;
    private String text;

    public Poster(String path, String text) {
        this.path = path;
        this.text = text;
    }

    public interface HttpListener<T> {
        T onResultBack(String result);
    }

    public void setHttpListener(HttpListener<T> httpListener) {
        this.httpListener = httpListener;
    }

    public T call() {
        T data_init = null;
        //get的方式提交就是url拼接的方式
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStream outputStream = connection.getOutputStream();
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
            outputStreamWriter.write(text);
            //获得结果码
            outputStreamWriter.close();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                //请求成功 获得返回的流
                InputStream inputStream = connection.getInputStream();
                int length = connection.getContentLength();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, length);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                for (int result = bufferedInputStream.read(); result != -1; result = bufferedInputStream.read()) {
                    byteArrayOutputStream.write((byte) result);
                }
                String result = byteArrayOutputStream.toString();
                data_init= httpListener.onResultBack(result);
            } else {
                //请求失败
                Log.e("Poster", "http error");

            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Poster", "get error");
        }
        return data_init;
    }

    public T post() {
        FutureTask<T> task = new FutureTask<>(this);
        new Thread(task).start();
        T data = null;
        try {
            data = task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
