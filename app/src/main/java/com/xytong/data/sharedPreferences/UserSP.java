package com.xytong.data.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xytong.data.UserData;

public class UserSP {

    public static String getToken(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("token", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    public static void setToken(Context context, String token) {
        SharedPreferences sharedPref = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.apply();
    }

    public static String getPwd(Context context) {//保存密码到本地
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("pwd", Context.MODE_PRIVATE);
        return sharedPreferences.getString("pwd", "");
    }

    public static void setPwd(Context context, String pwd) {
        SharedPreferences sharedPref = context.getSharedPreferences("pwd", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("pwd", pwd);
        editor.apply();
    }

    public static UserData getUser(Context context) {//保存密码到本地
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("user", "");
        ObjectMapper objectMapper = new ObjectMapper();
        UserData userData = new UserData();
        try {
            userData = objectMapper.readValue(json, UserData.class);
        } catch (JsonProcessingException e) {
            Log.e("setUser", "json error");
            e.printStackTrace();
        }
        return userData;
    }

    public static void setUser(Context context, UserData userData) {
        SharedPreferences sharedPref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        ObjectMapper postMapper = new ObjectMapper();
        try {
            editor.putString("user", postMapper.writeValueAsString(userData));
            editor.apply();
        } catch (JsonProcessingException e) {
            Log.e("setUser", "json error");
            e.printStackTrace();
        }
    }
}
