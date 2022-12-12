package com.xytong.dao;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.StringRes;
import com.xytong.R;

import java.util.Objects;

public interface SettingDao {
    String SH_URL_NAME = "sh_url";
    String USER_URL_NAME = "user_url";
    String RE_URL_NAME = "re_url";
    String FORUM_URL_NAME = "forum_url";
    String ACCESS_URL_NAME = "access_url";
    String COMMENT_URL_NAME = "comment_url";
    String CAPTCHA_URL_NAME = "captcha_url";
    @StringRes
    int SH_URL_RES = R.string.sh_url;
    @StringRes
    int USER_URL_RES = R.string.user_url;
    @StringRes
    int RE_URL_RES = R.string.re_url;
    @StringRes
    int FORUM_URL_RES = R.string.forum_url;
    @StringRes
    int ACCESS_URL_RES = R.string.access_url;
    @StringRes
    int COMMENT_URL_RES = R.string.comment_url;
    @StringRes
    int CAPTCHA_URL_RES = R.string.captcha_url;

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
}
