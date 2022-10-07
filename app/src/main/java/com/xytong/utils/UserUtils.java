package com.xytong.utils;

import android.content.Context;
import android.util.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xytong.dao.SettingDao;
import com.xytong.model.dto.UserRequestDTO;
import com.xytong.model.dto.UserPostDTO;
import com.xytong.utils.poster.Poster;

public class UserUtils {
    public static UserRequestDTO getUser(Context context, String username) {
        ObjectMapper postMapper = new ObjectMapper();
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
        userPostDTO.setUsername(username);
        try {
            Log.i("UserUtils.getUser())", "post ok");
            Poster<UserRequestDTO> poster = new Poster<>(SettingDao.getUserUrl(context),
                    postMapper.writeValueAsString(userPostDTO));
            poster.setHttpListener(result -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    UserRequestDTO userRequestDTO = objectMapper.readValue(result, UserRequestDTO.class);
                    Log.i("UserUtils.getUser())", "get ok:"+userRequestDTO.toString());
                    return userRequestDTO;//异步完成数据传递
                } catch (Exception e) {
                    Log.e("UserUtils.getUser())", "error");
                    e.printStackTrace();
                }
                return null;
            });
            return poster.post();//由于该方法被包裹在新线程进行，该线程会等待网络进程
        } catch (Exception e) {
            Log.e("UserUtils.getUser()", "error");
            e.printStackTrace();
        }
        return null;
    }
}
