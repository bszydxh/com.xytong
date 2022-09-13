package com.xytong.dao;

import android.content.Context;
import android.content.SharedPreferences;
import com.xytong.R;

import java.util.Objects;

public interface SettingDao {
    static boolean isDemonstrateMode(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("demonstrateMode", false);
    }

    static void setDemonstrateMode(Context context, boolean demonstrateMode) {
        SharedPreferences sharedPref = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("demonstrateMode", demonstrateMode);
        editor.apply();
    }

    static String getShUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("sh_url", Context.MODE_PRIVATE);
        String sh_url = sharedPreferences.getString("sh_url", "");
        if (Objects.equals(sh_url, "")) {
            setShUrl(context, context.getString(R.string.sh_url));
        }
        return sharedPreferences.getString("sh_url", "127.0.0.1");
    }

    static void setShUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences("sh_url", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("sh_url", url);
        editor.apply();
    }

    static String getReUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("re_url", Context.MODE_PRIVATE);
        String re_url = sharedPreferences.getString("re_url", "");
        if (Objects.equals(re_url, "")) {
            setReUrl(context, context.getString(R.string.re_url));
        }
        return sharedPreferences.getString("re_url", "127.0.0.1");
    }

    static void setReUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences("re_url", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("re_url", url);
        editor.apply();
    }

    static String getCommentUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("comment_url", Context.MODE_PRIVATE);
        String comment_url = sharedPreferences.getString("comment_url", "");
        if (Objects.equals(comment_url, "")) {
            setCommentUrl(context, context.getString(R.string.comment_url));
        }
        return sharedPreferences.getString("comment_url", "127.0.0.1");
    }

    static void setCommentUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences("comment_url", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("comment_url", url);
        editor.apply();
    }

    static String getForumUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("forum_url", Context.MODE_PRIVATE);
        String forum_url = sharedPreferences.getString("forum_url", "");
        if (Objects.equals(forum_url, "")) {
            setForumUrl(context, context.getString(R.string.forum_url));
        }
        return sharedPreferences.getString("forum_url", "127.0.0.1");
    }

    static void setForumUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences("forum_url", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("forum_url", url);
        editor.apply();
    }

    static String getAccessUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("access_url", Context.MODE_PRIVATE);
        String access_url = sharedPreferences.getString("access_url", "");
        if (Objects.equals(access_url, "")) {
            setAccessUrl(context, context.getString(R.string.access_url));
        }
        return sharedPreferences.getString("access_url", "127.0.0.1");
    }

    static void setAccessUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences("access_url", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("access_url", url);
        editor.apply();
    }
}
