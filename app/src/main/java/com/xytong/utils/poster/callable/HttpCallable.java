package com.xytong.utils.poster.callable;

import android.util.Log;
import com.xytong.utils.poster.HttpListener;
import com.xytong.utils.poster.Poster;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class HttpCallable<T> implements Callable<T> {
    private HttpListener<T> httpListener;
    private Poster.PostListener<T> posterListener;
    private String path = "";
    private String text = "";

    public HttpCallable(String path, String text, HttpListener<T> httpListener) {
        if (httpListener == null) {
            httpListener = result -> null;
        }
        this.path = path;
        this.text = text;
        this.httpListener = httpListener;
    }

    public HttpListener<T> setPosterListener(Poster.PostListener<T> postListener) {
        return httpListener;
    }

    public T call() {
        if (posterListener != null) {
            posterListener.onStart();
        }
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
                String result_back;
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
                if (posterListener != null) {
                    posterListener.onError("http error", responseCode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Poster", "io error");
            if (posterListener != null) {
                posterListener.onError("io error", 401);
            }
        }
        if (posterListener != null) {
            posterListener.onDone(data_init);
        }
        return data_init;
    }


}
