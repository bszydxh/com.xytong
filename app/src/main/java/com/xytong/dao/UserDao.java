package com.xytong.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xytong.model.vo.UserVO;

public interface UserDao {
    static String getToken(Context context) {
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("token", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }

    static void setToken(Context context, String token) {
        SharedPreferences sharedPref = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.apply();
    }

    static String getPwd(Context context) {//保存密码到本地
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("pwd", Context.MODE_PRIVATE);
        return sharedPreferences.getString("pwd", "");
    }

    static void setPwd(Context context, String pwd) {
        SharedPreferences sharedPref = context.getSharedPreferences("pwd", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("pwd", pwd);
        editor.apply();
    }

    static UserVO getUser(Context context) {//保存密码到本地
        SharedPreferences sharedPreferences =
                context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("user", "");
        ObjectMapper objectMapper = new ObjectMapper();
        UserVO userVO = new UserVO();
        try {
            userVO = objectMapper.readValue(json, UserVO.class);
        } catch (JsonProcessingException e) {
            Log.e("setUser", "json error");
            e.printStackTrace();
        }
        return userVO;
    }

    static void setUser(Context context, UserVO userVO) {
        SharedPreferences sharedPref = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        ObjectMapper postMapper = new ObjectMapper();
        try {
            editor.putString("user", postMapper.writeValueAsString(userVO));
            editor.apply();
        } catch (JsonProcessingException e) {
            Log.e("setUser", "json error");
            e.printStackTrace();
        }
    }
}
