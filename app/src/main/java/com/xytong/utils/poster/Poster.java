package com.xytong.utils.poster;


import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xytong.utils.AccessUtils;
import com.xytong.utils.poster.callable.HttpCallable;
import com.xytong.utils.poster.callable.HttpWithTokenCallable;

import java.util.concurrent.FutureTask;

import static java.lang.Thread.sleep;

public class Poster<T> {
    private HttpListener<T> httpListener;
    private PostListener<T> posterListener;
    private String path = "";
    private String text = "";


    public interface PostListener<T> {
        void onStart();

        void onDone(T data);

        void onError(String msg, int httpCode);
    }

    public Poster<T> setHttpListener(HttpListener<T> httpListener) {
        this.httpListener = httpListener;
        return this;
    }

    public Poster(String path, String text) {
        if (path != null) {
            this.path = path;
        }
        if (text != null) {
            this.text = text;
        }
    }

    public Poster<T> setPosterListener(PostListener<T> posterListener) {
        this.posterListener = posterListener;
        return this;
    }

    public static <T> Poster<T> init(String path, String text, Class<T> responseType) {
        Poster<T> poster = new Poster<>(path, text);
        if (path != null) {
            poster.path = path;
        }
        if (text != null) {
            poster.text = text;
        }
        return poster;
    }

    /**
     * 实时返回需要的数据，警告：会造成线程阻塞
     *
     * @return 可返回任意数据
     */
    public T post() {
        HttpCallable<T> tHttpCallable = new HttpCallable<>(path, text, httpListener);
        tHttpCallable.setPosterListener(posterListener);
        return tHttpCallable.call();
    }

    public static <T> T jacksonPost(String URL, @NonNull Object requestDTO, Class<T> responseDTOType) {
        ObjectMapper postMapper = new ObjectMapper();
        Class<?> postDTOClass = requestDTO.getClass();
        try {
            Log.i("Poster.jacksonPost()", "posting: " + postDTOClass.getName() + " -> server");
            return Poster.init(URL, postMapper.writeValueAsString(requestDTO), responseDTOType)
                    .setHttpListener(result -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();
                            T requestDTO_result = objectMapper.readValue(result, responseDTOType);
                            Log.i("Poster.jacksonPost()", "has response: server -> " + requestDTO_result.getClass().getName()
                                    + " \nresult:" + requestDTO_result);
                            return requestDTO_result;//异步完成数据传递
                        } catch (Exception e) {
                            Log.e("Poster.jacksonPost()", "jackson unpack error:" + e);
                        }
                        return null;
                    })//由于该方法被包裹在新线程进行，该线程会等待网络进程
                    .post();
        } catch (Exception e) {
            Log.e("Poster.jacksonPost()", "error" + e);
        }
        return null;
    }
}
