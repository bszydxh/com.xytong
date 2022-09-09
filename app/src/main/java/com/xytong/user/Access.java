package com.xytong.user;

import android.util.Base64;

import com.xytong.data.UserData;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.logging.Handler;

import javax.crypto.Cipher;


public class Access {

    public static String md5Salt(String userName, String pwd) {
        return md5(userName + pwd);
    }

    public static String md5(String content) {
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
    public static String rsaEncrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decode(publicKey,Base64.DEFAULT);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return Base64.encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)),Base64.DEFAULT);
    }
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
