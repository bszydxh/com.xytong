package com.xytong.utils;

import android.content.Context;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xytong.dao.SettingDao;
import com.xytong.dao.UserDao;
import com.xytong.model.dto.AccessPostDTO;
import com.xytong.model.dto.AccessResponseDTO;
import com.xytong.utils.poster.Poster;
import org.jetbrains.annotations.Nullable;

public class TokenDownloader {
    /**
     * 从网络获取token值，不管本地更新
     * @param context 传入安卓上下文，token值被包含在上下文当中
     * @param username 用户名
     * @param md5WithSalt 加盐过后的md5值
     * @return (null)查询不到则返回空值!注意!
     */
    @Nullable
    public static AccessResponseDTO getTokenFromServer(Context context, String username, String md5WithSalt) {
        ObjectMapper postMapper = new ObjectMapper();
        AccessPostDTO accessPostDTO = new AccessPostDTO();
        accessPostDTO.setTimestamp(System.currentTimeMillis());
        accessPostDTO.setUsername(username);
        accessPostDTO.setPassword(md5WithSalt);
        try {
            Log.i("TokenDownloader.getTokenFromServer())", "post ok");
            Poster<AccessResponseDTO> poster = new Poster<>(SettingDao.getAccessUrl(context),
                    postMapper.writeValueAsString(accessPostDTO));
            poster.setHttpListener(result -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    AccessResponseDTO accessResponseDTO = objectMapper.readValue(result, AccessResponseDTO.class);
                    Log.i("TokenDownloader.getTokenFromServer())", "get ok:"+ accessResponseDTO.toString());
                    return accessResponseDTO;//异步完成数据传递
                } catch (Exception e) {
                    Log.e("TokenDownloader.getTokenFromServer())", "error");
                    e.printStackTrace();
                }
                return null;
            });
            return poster.post();//由于该方法被包裹在新线程进行，该线程会等待网络进程
        } catch (Exception e) {
            Log.e("TokenDownloader.getToken()", "error");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 联网校验/更新本地储存的token值
     * @param context 传入安卓上下文，token值被包含在上下文当中
     * @return
     */
    @Nullable
    public static AccessResponseDTO checkTokenFromServer(Context context) {
        ObjectMapper postMapper = new ObjectMapper();
        AccessPostDTO accessPostDTO = new AccessPostDTO();
        accessPostDTO.setTimestamp(System.currentTimeMillis());
        accessPostDTO.setToken(UserDao.getToken(context));
        try {
            Log.i("TokenDownloader.getToken())", "post ok");
            Poster<AccessResponseDTO> poster = new Poster<>(SettingDao.getAccessUrl(context),
                    postMapper.writeValueAsString(accessPostDTO));
            poster.setHttpListener(result -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    AccessResponseDTO accessResponseDTO = objectMapper.readValue(result, AccessResponseDTO.class);
                    Log.i("TokenDownloader.getToken())", "get ok:"+ accessResponseDTO.toString());
                    return accessResponseDTO;//异步完成数据传递
                } catch (Exception e) {
                    Log.e("TokenDownloader.getToken())", "error");
                    e.printStackTrace();
                }
                return null;
            });
            return poster.post();//由于该方法被包裹在新线程进行，该线程会等待网络进程
        } catch (Exception e) {
            Log.e("TokenDownloader.getToken()", "error");
            e.printStackTrace();
        }
        return null;
    }
}
