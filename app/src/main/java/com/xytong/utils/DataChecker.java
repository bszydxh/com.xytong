package com.xytong.utils;

import android.content.Context;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xytong.dao.SettingDao;
import com.xytong.model.dto.AccessPostDTO;
import com.xytong.model.dto.AccessRequestDTO;
import com.xytong.utils.http.Poster;

public class DataChecker {

    /**
     * @param context
     * @param username
     * @param md5WithSalt MD5且加盐后的密码
     * @return 返回空对象则为获取失败
     */
    public static AccessRequestDTO getToken(Context context, String username, String md5WithSalt) {
        ObjectMapper postMapper = new ObjectMapper();
        AccessPostDTO accessPostDTO = new AccessPostDTO();
        accessPostDTO.setTimestamp(System.currentTimeMillis());
        accessPostDTO.setUsername(username);
        accessPostDTO.setPassword(md5WithSalt);
        try {
            Log.i("DataChecker.getToken())", "post ok");
            Poster<AccessRequestDTO> poster = new Poster<>(SettingDao.getAccessUrl(context),
                    postMapper.writeValueAsString(accessPostDTO));
            poster.setHttpListener(result -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    AccessRequestDTO accessRequestDTO = objectMapper.readValue(result, AccessRequestDTO.class);
                    Log.i("DataChecker.getToken())", "get ok");
                    return accessRequestDTO;//异步完成数据传递
                } catch (Exception e) {
                    Log.e("DataChecker.getToken())", "error");
                    e.printStackTrace();
                }
                return null;
            });
            return poster.post();//由于该方法被包裹在新线程进行，该线程会等待网络进程
        } catch (Exception e) {
            Log.e("DataChecker.getToken()", "error");
            e.printStackTrace();
        }
        return null;
    }
}
