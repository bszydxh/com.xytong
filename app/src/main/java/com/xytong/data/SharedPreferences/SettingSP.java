package com.xytong.data.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

public class SettingSP {
    String shUrl;
    String reUrl;
    String forumUrl;
    String commentUrl;

    public static boolean isDemonstrateMode(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("demonstrateMode", false);
    }

    public static String getShUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("sh_url", Context.MODE_PRIVATE);
        String sh_url = sharedPreferences.getString("sh_url", "");
        if (Objects.equals(sh_url, "")) {
            setShUrl(context, "http://rap2api.taobao.org/app/mock/data/2307034");
        }
        return sharedPreferences.getString("sh_url", "127.0.0.1");
    }

    public static String getReUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("re_url", Context.MODE_PRIVATE);
        String re_url = sharedPreferences.getString("re_url", "");
        if (Objects.equals(re_url, "")) {
            setReUrl(context, "http://rap2api.taobao.org/app/mock/data/2307037");
        }
        return sharedPreferences.getString("re_url", "127.0.0.1");
    }

    public static String getCommentUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("comment_url", Context.MODE_PRIVATE);
        String comment_url = sharedPreferences.getString("comment_url", "");
        if (Objects.equals(comment_url, "")) {
            setCommentUrl(context, "http://rap2api.taobao.org/app/mock/data/2255088");
        }
        return sharedPreferences.getString("comment_url", "127.0.0.1");
    }

    public static String getForumUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("forum_url", Context.MODE_PRIVATE);
        String forum_url = sharedPreferences.getString("forum_url", "");
        if (Objects.equals(forum_url, "")) {
            setForumUrl(context, "http://rap2api.taobao.org/app/mock/data/2255088");
        }
        return sharedPreferences.getString("forum_url", "127.0.0.1");
    }

    public static void setCommentUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences("comment_url", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("comment_url", url);
        editor.apply();
    }

    public static void setForumUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences("forum_url", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("forum_url", url);
        editor.apply();
    }

    public static void setReUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences("re_url", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("re_url", url);
        editor.apply();
    }

    public static void setShUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences("sh_url", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("sh_url", url);
        editor.apply();
    }

    public static void setDemonstrateMode(Context context, boolean demonstrateMode) {
        SharedPreferences sharedPref = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("demonstrateMode", demonstrateMode);
        editor.apply();
    }
}
