package com.xytong.utils.http;


import android.content.Context;
import com.xytong.utils.Access;
import com.xytong.utils.http.callable.HttpCallable;
import com.xytong.utils.http.callable.HttpWithTokenCallable;

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

    public void setHttpListener(HttpListener<T> httpListener) {
        this.httpListener = httpListener;
    }

    public Poster(String path, String text) {
        if (path != null) {
            this.path = path;
        }
        if (text != null) {
            this.text = text;
        }
    }

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
       Access.getTokenForHttp(context, new Access.TokenListener() {
            @Override
            public void onStart(Context context) {
                posterListener.onStart(context);
            }

            @Override
            public void onDone(Context context, String token) {
                FutureTask<T> task = new FutureTask<>(new HttpWithTokenCallable<T>(path, text, token, httpListener));
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
            public void onError(Context context) {

            }
        });
       return this;
    }
}
