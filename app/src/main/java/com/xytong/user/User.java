package com.xytong.user;

import com.xytong.data.UserData;

import java.util.logging.Handler;

public class User {
    //TODO
    //此处返回值给前端看的
    //handle用于失败弹窗
    public static boolean login(Long id, String pwd, Handler handler) {
        return false;
    }

    public static boolean logon(Long id, String pwd, UserData userData, Handler handler) {
        return false;
    }

    public static boolean getTokenForStart(Long id, int reason, Handler handler) {//http鉴权以及启动鉴权用
        return false;
        //启动时更新token
    }

    public static boolean getTokenForHttp(Long id, int reason, Handler handler) {//http鉴权以及启动鉴权用
        //分登录/未登录两种状态
        //未登录进行强制跳转
        return false;
    }

    public static boolean startCheck(Long id, String token, Handler handler) {
        return false;
    }


}
