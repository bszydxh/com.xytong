package com.xytong.dao;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.StringRes;
import com.xytong.R;

import java.util.Objects;

public interface SettingDao {
    static String SH_URL_NAME = "sh_url";
    static String USER_URL_NAME = "user_url";
    static String RE_URL_NAME = "re_url";
    static String FORUM_URL_NAME = "forum_url";
    static String ACCESS_URL_NAME = "access_url";
    static String COMMENT_URL_NAME = "comment_url";
    @StringRes
    static int SH_URL_RES = R.string.sh_url;
    @StringRes
    static int USER_URL_RES = R.string.user_url;
    @StringRes
    static int RE_URL_RES = R.string.re_url;
    @StringRes
    static int FORUM_URL_RES = R.string.forum_url;
    @StringRes
    static int ACCESS_URL_RES = R.string.access_url;
    @StringRes
    static int COMMENT_URL_RES = R.string.comment_url;

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

    static String getUrl(Context context, String target_address, @StringRes int resId) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(target_address, Context.MODE_PRIVATE);
        String sh_url = sharedPreferences.getString(target_address, "");
        if (Objects.equals(sh_url, "")) {
            setUrl(context, target_address, context.getString(resId));
        }
        return sharedPreferences.getString(target_address, context.getString(resId));
    }

    static void setUrl(Context context, String target_address, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences(target_address, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(target_address, url);
        editor.apply();
    }

    @Deprecated
    static String getShUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(SH_URL_NAME, Context.MODE_PRIVATE);
        String sh_url = sharedPreferences.getString(SH_URL_NAME, "");
        if (Objects.equals(sh_url, "")) {
            setShUrl(context, context.getString(SH_URL_RES));
        }
        return sharedPreferences.getString(SH_URL_NAME, context.getString(SH_URL_RES));
    }

    @Deprecated
    static void setShUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences(SH_URL_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(SH_URL_NAME, url);
        editor.apply();
    }

    @Deprecated
    static String getReUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(RE_URL_NAME, Context.MODE_PRIVATE);
        String re_url = sharedPreferences.getString(RE_URL_NAME, "");
        if (Objects.equals(re_url, "")) {
            setReUrl(context, context.getString(RE_URL_RES));
        }
        return sharedPreferences.getString(RE_URL_NAME, context.getString(RE_URL_RES));
    }

    @Deprecated
    static void setReUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences(RE_URL_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(RE_URL_NAME, url);
        editor.apply();
    }

    @Deprecated
    static String getCommentUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(COMMENT_URL_NAME, Context.MODE_PRIVATE);
        String comment_url = sharedPreferences.getString(COMMENT_URL_NAME, "");
        if (Objects.equals(comment_url, "")) {
            setCommentUrl(context, context.getString(COMMENT_URL_RES));
        }
        return sharedPreferences.getString(COMMENT_URL_NAME, context.getString(COMMENT_URL_RES));
    }

    @Deprecated
    static void setCommentUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences(COMMENT_URL_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(COMMENT_URL_NAME, url);
        editor.apply();
    }

    @Deprecated
    static String getForumUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(FORUM_URL_NAME, Context.MODE_PRIVATE);
        String forum_url = sharedPreferences.getString(FORUM_URL_NAME, "");
        if (Objects.equals(forum_url, "")) {
            setForumUrl(context, context.getString(FORUM_URL_RES));
        }
        return sharedPreferences.getString(FORUM_URL_NAME, context.getString(FORUM_URL_RES));
    }

    @Deprecated
    static void setForumUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences(FORUM_URL_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(FORUM_URL_NAME, url);
        editor.apply();
    }

    @Deprecated
    static String getAccessUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(ACCESS_URL_NAME, Context.MODE_PRIVATE);
        String access_url = sharedPreferences.getString(ACCESS_URL_NAME, "");
        if (Objects.equals(access_url, "")) {
            setAccessUrl(context, context.getString(ACCESS_URL_RES));
        }
        return sharedPreferences.getString(ACCESS_URL_NAME, context.getString(ACCESS_URL_RES));
    }

    @Deprecated
    static void setAccessUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences(ACCESS_URL_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ACCESS_URL_NAME, url);
        editor.apply();
    }

    @Deprecated
    static String getUserUrl(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences(USER_URL_NAME, Context.MODE_PRIVATE);
        String access_url = sharedPreferences.getString(USER_URL_NAME, "");
        if (Objects.equals(access_url, "")) {
            setAccessUrl(context, context.getString(USER_URL_RES));
        }
        return sharedPreferences.getString(USER_URL_NAME, context.getString(USER_URL_RES));
    }

    @Deprecated
    static void setUserUrl(Context context, String url) {
        SharedPreferences sharedPref = context.getSharedPreferences(USER_URL_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(USER_URL_NAME, url);
        editor.apply();
    }
}
