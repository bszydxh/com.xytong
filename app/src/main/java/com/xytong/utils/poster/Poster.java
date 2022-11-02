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
        void onStart(Context context);

        void onDone(Context context, T data);

        void onError(Context context);
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

//    public static <T> Poster<T> init(String path, String text) {
//        Poster<T> poster = new Poster<T>();
//        if (path != null) {
//            poster.path = path;
//        }
//        if (text != null) {
//            poster.text = text;
//        }
//        return poster;
//    }

    /**
     * 实时返回需要的数据，警告：会造成线程阻塞
     *
     * @return 可返回任意数据
     */
    public T post() {
        FutureTask<T> task = new FutureTask<>(new HttpCallable<T>(path, text, httpListener));
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

    public Poster<T> postWithToken(Context context, PostListener<T> posterListener) {
        AccessUtils.getTokenForHttp(context, new AccessUtils.TokenListener() {
            @Override
            public void onStart(Context context) {
                posterListener.onStart(context);
            }

            @Override
            public void onDone(Context context, String token) {
                FutureTask<T> task = new FutureTask<>(new HttpWithTokenCallable<>(path, text, token, httpListener));
                new Thread(task).start();
                T data = null;
                try {
                    sleep(1);//交钱优化
                    data = task.get();//这个方法阻塞主线程
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (token == null) {
                    posterListener.onError(context);
                }
                posterListener.onDone(context, data);
            }

            @Override
            public void onError(Context context, int errorFlag) {

            }
        });
        return this;
    }
    public static <T> T jacksonPost(String URL, @NonNull Object requestDTO, Class<T> responseDTOType) {
        ObjectMapper postMapper = new ObjectMapper();
        Class<?> postDTOClass = requestDTO.getClass();
        try {
            Log.i("Poster.jacksonPost()", "posting: " + postDTOClass.getName() + " -> server");
            Poster<T> poster = new Poster<>(URL, postMapper.writeValueAsString(requestDTO));
            poster.setHttpListener(result -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    T requestDTO_result = objectMapper.readValue(result, responseDTOType);
                    Log.i("Poster.jacksonPost()", "has response: server -> " + requestDTO_result.getClass().getName()
                            + " \nresult:" + requestDTO_result.toString());
                    return requestDTO_result;//异步完成数据传递
                } catch (Exception e) {
                    Log.e("Poster.jacksonPost()", "jackson unpack error:" + e.toString());
                }
                return null;
            });//由于该方法被包裹在新线程进行，该线程会等待网络进程
            return poster.post();
        } catch (Exception e) {
            Log.e("Poster.jacksonPost()", "error" + e.toString());
        }
        return null;
    }
}
