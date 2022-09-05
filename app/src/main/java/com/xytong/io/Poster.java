package com.xytong.io;


import static java.lang.Thread.sleep;

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
    private OutputStream outputStream;

    public Poster(String path, String text) {
        if (path != null) {
            this.path = path;
        }
        if (text != null) {
            this.text = text;
        }
    }

    public interface HttpListener<T> {
        /**
         * http监听器唯一接口
         *
         * @return 返回需要监听的数据
         */
        T onResultBack(String result);
    }

    /**
     * 设置http ok回调
     *
     * @param httpListener http监听器
     */
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
                String result_back = "";
                InputStream inputStream = connection.getInputStream();
                int length = connection.getContentLength();
                if (length >= 0) {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, length);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    for (int result = bufferedInputStream.read(); result != -1; result = bufferedInputStream.read()) {
                        byteArrayOutputStream.write((byte) result);
                    }
                    result_back = byteArrayOutputStream.toString();
                } else {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    for (int result = inputStream.read(); result != -1; result = inputStream.read()) {
                        byteArrayOutputStream.write((byte) result);
                    }
                    result_back = byteArrayOutputStream.toString();
                }
                data_init = httpListener.onResultBack(result_back);
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

    /**
     * 实时返回需要的数据，警告：会造成线程阻塞
     *
     * @return 可返回任意数据
     */
    public T post() {
        FutureTask<T> task = new FutureTask<>(this);
        new Thread(task).start();
        T data = null;
        try {
            sleep(1);//交钱优化
            data = task.get();//这个方法阻塞主线程
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
