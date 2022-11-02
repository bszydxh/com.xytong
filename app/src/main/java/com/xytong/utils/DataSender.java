package com.xytong.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.xytong.dao.SettingDao;
import com.xytong.dao.UserDao;
import com.xytong.model.dto.forum.ForumAddRequestDTO;
import com.xytong.model.dto.forum.ForumAddResponseDTO;
import com.xytong.model.dto.re.ReAddRequestDTO;
import com.xytong.model.dto.re.ReAddResponseDTO;
import com.xytong.model.dto.sh.ShAddRequestDTO;
import com.xytong.model.dto.sh.ShAddResponseDTO;
import com.xytong.utils.poster.Poster;

import java.math.BigDecimal;
import java.util.Objects;

public class DataSender {
    public static void sendForum(Context context, String title, String text) {
        new Thread(() -> {
            ForumAddRequestDTO forumAddRequestDTO = new ForumAddRequestDTO();
            forumAddRequestDTO.setText(text);
            forumAddRequestDTO.setTitle(title);
            forumAddRequestDTO.setToken(UserDao.getToken(context));
            forumAddRequestDTO.setUsername(UserDao.getUser(context).getName());
            forumAddRequestDTO.setModule("forums");
            forumAddRequestDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
            ForumAddResponseDTO forumAddResponseDTO = Poster.jacksonPost(
                    SettingDao.getUrl(context, SettingDao.FORUM_URL_NAME, SettingDao.FORUM_URL_RES) + "/add",
                    forumAddRequestDTO,
                    ForumAddResponseDTO.class);
            Handler handler = new Handler(Looper.getMainLooper());
            if (forumAddResponseDTO == null) {
                handler.post(() ->
                        Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show());
                return;
            }
            if (Objects.equals(forumAddResponseDTO.getMode(), "add ok")) {
                handler.post(() ->
                        Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show());
            } else {
                handler.post(() ->
                        Toast.makeText(context, "发布失败", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public static void sendSh(Context context, String title, String text, BigDecimal price) {
        new Thread(() -> {
            ShAddRequestDTO shAddRequestDTO = new ShAddRequestDTO();
            shAddRequestDTO.setText(text);
            shAddRequestDTO.setTitle(title);
            shAddRequestDTO.setPrice(price.toString());
            shAddRequestDTO.setToken(UserDao.getToken(context));
            shAddRequestDTO.setUsername(UserDao.getUser(context).getName());
            shAddRequestDTO.setModule("secondhand");
            shAddRequestDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
            ShAddResponseDTO shAddResponseDTO = Poster.jacksonPost(
                    SettingDao.getUrl(context, SettingDao.SH_URL_NAME, SettingDao.SH_URL_RES) + "/add",
                    shAddRequestDTO,
                    ShAddResponseDTO.class);
            Handler handler = new Handler(Looper.getMainLooper());
            if (shAddResponseDTO == null) {
                handler.post(() ->
                        Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show());
                return;
            }
            if (Objects.equals(shAddResponseDTO.getMode(), "add ok")) {
                handler.post(() ->
                        Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show());
            } else {
                handler.post(() ->
                        Toast.makeText(context, "发布失败", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    public static void sendRe(Context context, String title, String text, BigDecimal price) {
        new Thread(() -> {
            ReAddRequestDTO reAddRequestDTO = new ReAddRequestDTO();
            reAddRequestDTO.setText(text);
            reAddRequestDTO.setTitle(title);
            reAddRequestDTO.setPrice(price.toString());
            reAddRequestDTO.setToken(UserDao.getToken(context));
            reAddRequestDTO.setUsername(UserDao.getUser(context).getName());
            reAddRequestDTO.setModule("run_errands");
            reAddRequestDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
            ReAddResponseDTO reAddResponseDTO = Poster.jacksonPost(
                    SettingDao.getUrl(context, SettingDao.RE_URL_NAME, SettingDao.RE_URL_RES) + "/add",
                    reAddRequestDTO,
                    ReAddResponseDTO.class);
            Handler handler = new Handler(Looper.getMainLooper());
            if (reAddResponseDTO == null) {
                handler.post(() ->
                        Toast.makeText(context, "网络错误", Toast.LENGTH_SHORT).show());
                return;
            }
            if (Objects.equals(reAddResponseDTO.getMode(), "add ok")) {
                handler.post(() ->
                        Toast.makeText(context, "发布成功", Toast.LENGTH_SHORT).show());
            } else {
                handler.post(() ->
                        Toast.makeText(context, "发布失败", Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
