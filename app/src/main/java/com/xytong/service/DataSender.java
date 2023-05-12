package com.xytong.service;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.xytong.dao.SettingDao;
import com.xytong.dao.UserDao;
import com.xytong.model.dto.comment.CommentAddRequestDTO;
import com.xytong.model.dto.comment.CommentAddResponseDTO;
import com.xytong.model.dto.forum.ForumAddRequestDTO;
import com.xytong.model.dto.forum.ForumAddResponseDTO;
import com.xytong.model.dto.re.ReAddRequestDTO;
import com.xytong.model.dto.re.ReAddResponseDTO;
import com.xytong.model.dto.sh.ShAddRequestDTO;
import com.xytong.model.dto.sh.ShAddResponseDTO;
import com.xytong.utils.poster.Poster;

import java.math.BigDecimal;
import java.util.Objects;

import static com.xytong.service.DataDownloader.*;

public class DataSender {
    public static final int NETWORK_ERROR = -1;
    public static final int SEND_ERROR = -2;

    public interface StateListener {
        void onSuccess(Context context);

        void onStart(Context context);

        void onFalse(Context context, int error_flag);
    }

    public static StateListener getBaseStateListener() {
        return new StateListener() {
            @Override
            public void onSuccess(Context context) {
                Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStart(Context context) {
            }

            @Override
            public void onFalse(Context context, int error_flag) {
                switch (error_flag) {
                    case NETWORK_ERROR:
                        Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show();
                        break;
                    case SEND_ERROR:
                        Toast.makeText(context, "发布失败", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    public static void sendForum(Context context, String title, String text, StateListener listener) {
        new Thread(() -> {
            ForumAddRequestDTO forumAddRequestDTO = new ForumAddRequestDTO();
            forumAddRequestDTO.setText(text);
            forumAddRequestDTO.setTitle(title);
            forumAddRequestDTO.setToken(UserDao.getToken(context));
            forumAddRequestDTO.setUsername(UserDao.getUser(context).getName());
            forumAddRequestDTO.setModule(FORUM_MODULE_NAME);
            forumAddRequestDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
            ForumAddResponseDTO forumAddResponseDTO = Poster.jacksonPost(
                    SettingDao.getUrl(context, SettingDao.FORUM_URL_NAME, SettingDao.FORUM_URL_RES) + "/add",
                    forumAddRequestDTO,
                    ForumAddResponseDTO.class);
            Handler handler = new Handler(Looper.getMainLooper());
            if (forumAddResponseDTO == null) {
                handler.post(() -> listener.onFalse(context, NETWORK_ERROR));
                return;
            }
            if (Objects.equals(forumAddResponseDTO.getMode(), "add ok")) {
                handler.post(() -> listener.onSuccess(context));
            } else {
                handler.post(() -> listener.onFalse(context, SEND_ERROR));
            }
        }).start();
    }

    public static void sendForum(Context context, String title, String text) {
        sendForum(context, title, text, getBaseStateListener());
    }


    public static void sendSh(Context context, String title, String text, BigDecimal price, StateListener listener) {
        new Thread(() -> {
            ShAddRequestDTO shAddRequestDTO = new ShAddRequestDTO();
            shAddRequestDTO.setText(text);
            shAddRequestDTO.setTitle(title);
            shAddRequestDTO.setPrice(price.toString());
            shAddRequestDTO.setToken(UserDao.getToken(context));
            shAddRequestDTO.setUsername(UserDao.getUser(context).getName());
            shAddRequestDTO.setModule(SH_MODULE_NAME);
            shAddRequestDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
            ShAddResponseDTO shAddResponseDTO = Poster.jacksonPost(
                    SettingDao.getUrl(context, SettingDao.SH_URL_NAME, SettingDao.SH_URL_RES) + "/add",
                    shAddRequestDTO,
                    ShAddResponseDTO.class);
            Handler handler = new Handler(Looper.getMainLooper());
            if (shAddResponseDTO == null) {
                handler.post(() -> listener.onFalse(context, NETWORK_ERROR));
                return;
            }
            if (Objects.equals(shAddResponseDTO.getMode(), "add ok")) {
                handler.post(() -> listener.onSuccess(context));
            } else {
                handler.post(() -> listener.onFalse(context, SEND_ERROR));
            }
        }).start();
    }

    public static void sendSh(Context context, String title, String text, BigDecimal price) {
        sendSh(context, title, text, price, getBaseStateListener());
    }

    public static void sendRe(Context context, String title, String text, BigDecimal price, StateListener listener) {
        new Thread(() -> {
            ReAddRequestDTO reAddRequestDTO = new ReAddRequestDTO();
            reAddRequestDTO.setText(text);
            reAddRequestDTO.setTitle(title);
            reAddRequestDTO.setPrice(price.toString());
            reAddRequestDTO.setToken(UserDao.getToken(context));
            reAddRequestDTO.setUsername(UserDao.getUser(context).getName());
            reAddRequestDTO.setModule(RE_MODULE_NAME);
            reAddRequestDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
            ReAddResponseDTO reAddResponseDTO = Poster.jacksonPost(
                    SettingDao.getUrl(context, SettingDao.RE_URL_NAME, SettingDao.RE_URL_RES) + "/add",
                    reAddRequestDTO,
                    ReAddResponseDTO.class);
            Handler handler = new Handler(Looper.getMainLooper());
            if (reAddResponseDTO == null) {
                handler.post(() -> listener.onFalse(context, NETWORK_ERROR));
                return;
            }
            if (Objects.equals(reAddResponseDTO.getMode(), "add ok")) {
                handler.post(() -> listener.onSuccess(context));
            } else {
                handler.post(() -> listener.onFalse(context, NETWORK_ERROR));
            }
        }).start();
    }

    public static void sendRe(Context context, String title, String text, BigDecimal price) {
        sendRe(context, title, text, price, getBaseStateListener());
    }

    public static void sendComment(Context context, String module, Long cid, String text, StateListener listener) {
        new Thread(() -> {
            CommentAddRequestDTO commentAddRequestDTO = new CommentAddRequestDTO();
            commentAddRequestDTO.setText(text);
            commentAddRequestDTO.setToken(UserDao.getToken(context));
            commentAddRequestDTO.setUsername(UserDao.getUser(context).getName());
            commentAddRequestDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
            commentAddRequestDTO.setCid(String.valueOf(cid));
            commentAddRequestDTO.setModule(module);
            CommentAddResponseDTO commentAddResponseDTO = Poster.jacksonPost(
                    SettingDao.getUrl(context, SettingDao.COMMENT_URL_NAME, SettingDao.COMMENT_URL_RES) + "/add",
                    commentAddRequestDTO,
                    CommentAddResponseDTO.class);
            Handler handler = new Handler(Looper.getMainLooper());
            if (commentAddResponseDTO == null) {
                handler.post(() -> listener.onFalse(context, NETWORK_ERROR));
                return;
            }
            if (Objects.equals(commentAddResponseDTO.getMode(), "add ok")) {
                handler.post(() -> listener.onSuccess(context));
            } else {
                handler.post(() -> listener.onFalse(context, SEND_ERROR));
            }
        }).start();
    }

    public static void sendComment(Context context, String module, Long cid, String text) {
        sendComment(context, module, cid, text, getBaseStateListener());
    }
}
