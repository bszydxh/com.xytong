package com.xytong.dao;

import android.content.Context;
import android.content.SharedPreferences;
import org.jetbrains.annotations.NotNull;

public interface ListTimeDao {
    @NotNull
    static Long getForumTime(@NotNull Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("forum_time", Context.MODE_PRIVATE);
        return sharedPreferences.getLong("forum_time", System.currentTimeMillis());
    }

    static void setForumTime(@NotNull Context context, Long timestamp) {
        SharedPreferences sharedPref = context.getSharedPreferences("forum_time", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("forum_time", timestamp);
        editor.apply();
    }

    @NotNull
    static Long getShTime(@NotNull Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("sh_time", Context.MODE_PRIVATE);
        return sharedPreferences.getLong("sh_time", System.currentTimeMillis());
    }

    static void setShTime(@NotNull Context context, Long timestamp) {
        SharedPreferences sharedPref = context.getSharedPreferences("sh_time", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("sh_time", timestamp);
        editor.apply();
    }

    @NotNull
    static Long getReTime(@NotNull Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("re_time", Context.MODE_PRIVATE);
        return sharedPreferences.getLong("re_time", System.currentTimeMillis());
    }

    static void setReTime(@NotNull Context context, Long timestamp) {
        SharedPreferences sharedPref = context.getSharedPreferences("re_time", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("re_time", timestamp);
        editor.apply();
    }
}
