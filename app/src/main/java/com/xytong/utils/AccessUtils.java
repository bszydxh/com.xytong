package com.xytong.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import com.xytong.dao.UserDao;
import com.xytong.model.dto.AccessResponseDTO;
import com.xytong.model.dto.UserResponseDTO;
import com.xytong.model.vo.UserVO;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import static java.lang.Thread.sleep;


public class AccessUtils {
    public static final int USER_NOT_FOUND_ERROR = -1;
    public static final int SERVER_ERROR = -2;
    public static final int TOKEN_EXPIRED_ERROR = -3;
    public static final int USERNAME_OR_PASSWORD_ERROR = -4;

    public interface UserDataListener {
        public void onStart(Context context);

        public void onDone(Context context, UserVO userVO);

        public void onError(Context context, int errorFlag);
    }

    public interface TokenListener {
        public void onStart(Context context);

        public void onDone(Context context, String token);

        public void onError(Context context, int errorFlag);
    }

    public interface StatusListener {
        public void onStart(Context context);

        public void onDone(Context context);

        public void onError(Context context, int errorFlag);
    }

    public static String md5Salt(String userName, String pwd) {
        return md5(userName + pwd);
    }

    private static String md5(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        }
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {//按位迭代
            if ((b & 0xFF) < 0x10) {//如果取256余,小于16插入0;
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    private static String rsaEncrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decode(publicKey, Base64.DEFAULT);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)), Base64.DEFAULT);
    }

    public static void login(Context context, String username, String pwd, StatusListener statusListener) {
        new Thread(() -> {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> statusListener.onStart(context));
            boolean error_flag = false;
            AccessResponseDTO accessResponseDTO;
            //耗时操作
            try {
                accessResponseDTO = TokenDownloader.getTokenFromServer(context, username, md5Salt(username, pwd));
                Log.i("login", "get ok");
            } catch (Exception e) {
                Log.e("login", "error");
                handler.post(() -> statusListener.onError(context, SERVER_ERROR));
                e.printStackTrace();
                return;
            }
            if (accessResponseDTO == null) {
                Log.w("login", "dto null");
                handler.post(() -> statusListener.onError(context, SERVER_ERROR));
                return;
            }

            if (accessResponseDTO.getToken() == null) {
                Log.w("login", "null token");
                handler.post(() -> statusListener.onError(context, USERNAME_OR_PASSWORD_ERROR));
                return;
            }
            if (accessResponseDTO.getToken().trim().equals("")) {
                Log.w("login", "no token");
                handler.post(() -> statusListener.onError(context, USERNAME_OR_PASSWORD_ERROR));
                return;
            }
            Log.i("login", "check ok");
            UserDao.setToken(context, accessResponseDTO.getToken());
            UserResponseDTO userResponseDTO = UserDownloader.getUser(context, username);
            UserVO userVO = UserVO.init(userResponseDTO);
            if (userVO != null) {
                UserDao.setUser(context, userVO);
            } else {
                Log.i("login", "get userVO error");
            }
            handler.post(() -> statusListener.onDone(context));
        }).start();

    }

    public static void logon(Context context, UserVO userVO, String pwd, StatusListener statusListener) {

    }

    /**
     * 异步操作，需要回调处理需要的数据，警告：会造成线程阻塞
     */
    public static void getTokenForStart(Context context, UserDataListener listener) {//http鉴权以及启动鉴权用
        //启动时更新token，异步操作，无法获取操作状态，获取失败进入未登录状态（userDataSP置空）
        UserVO userVO = UserDao.getUser(context);
        new Thread(() -> {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> listener.onStart(context));
            //耗时操作

            if (true) {
                handler.post(() -> listener.onDone(context, userVO));
            } else {
                handler.post(() -> listener.onError(context, -1));
            }
        }).start();
    }

    /**
     * 异步操作，需要回调处理需要的数据，警告：会造成线程阻塞
     */
    public static void getTokenForHttp(Context context, TokenListener listener) {//http鉴权以及启动鉴权用
        //分登录/未登录两种状态
        //未登录进行强制跳转
        //不异步处理
        UserVO userVO = UserDao.getUser(context);

        new Thread(() -> {
            Handler handler = new Handler(Looper.getMainLooper());
            handler.post(() -> listener.onStart(context));
            //耗时操作
            try {
                sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (true) {
                handler.post(() -> listener.onDone(context, ""));
            } else {
                handler.post(() -> listener.onError(context, -1));
            }
        }).start();
    }
}
