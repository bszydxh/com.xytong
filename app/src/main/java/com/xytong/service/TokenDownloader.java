package com.xytong.service;

import android.content.Context;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xytong.dao.SettingDao;
import com.xytong.dao.UserDao;
import com.xytong.model.dto.access.AccessCheckRequestDTO;
import com.xytong.model.dto.access.AccessCheckResponseDTO;
import com.xytong.utils.poster.Poster;
import org.jetbrains.annotations.Nullable;

public class TokenDownloader {
    /**
     * 从网络获取token值，不管本地更新
     *
     * @param context     传入安卓上下文，token值被包含在上下文当中
     * @param username    用户名
     * @param md5WithSalt 加盐过后的md5值
     * @return (null)查询不到则返回空值!注意!
     */
    @Nullable
    public static AccessCheckResponseDTO getTokenFromServer(Context context, String username, String md5WithSalt) {
        ObjectMapper postMapper = new ObjectMapper();
        AccessCheckRequestDTO accessCheckRequestDTO = new AccessCheckRequestDTO();
        accessCheckRequestDTO.setTimestamp(System.currentTimeMillis());
        accessCheckRequestDTO.setUsername(username);
        accessCheckRequestDTO.setPassword(md5WithSalt);
        return Poster.jacksonPost(
                SettingDao.getUrl(context, SettingDao.ACCESS_URL_NAME, SettingDao.ACCESS_URL_RES) + "/check",
                accessCheckRequestDTO,
                AccessCheckResponseDTO.class
        );
    }

    /**
     * 联网校验/更新本地储存的token值
     *
     * @param context 传入安卓上下文，token值被包含在上下文当中
     * @return (null)查询不到则返回空值!注意!
     */
    @Nullable
    public static AccessCheckResponseDTO checkTokenFromServer(Context context) {
        AccessCheckRequestDTO accessCheckRequestDTO = new AccessCheckRequestDTO();
        accessCheckRequestDTO.setTimestamp(System.currentTimeMillis());
        accessCheckRequestDTO.setToken(UserDao.getToken(context));
        return Poster.jacksonPost(
                SettingDao.getUrl(context, SettingDao.ACCESS_URL_NAME, SettingDao.ACCESS_URL_RES) + "/check",
                accessCheckRequestDTO,
                AccessCheckResponseDTO.class
        );
    }
}
