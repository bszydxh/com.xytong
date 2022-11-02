package com.xytong.utils;

import android.content.Context;
import com.xytong.dao.SettingDao;
import com.xytong.model.dto.user.UserRequestDTO;
import com.xytong.model.dto.user.UserResponseDTO;
import com.xytong.utils.poster.Poster;

public class UserDownloader {
    public static UserResponseDTO getUser(Context context, String username) {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
        userRequestDTO.setUsername(username);
        return Poster.jacksonPost(
                SettingDao.getUrl(context, SettingDao.USER_URL_NAME, SettingDao.USER_URL_RES) + "/get",
                userRequestDTO,
                UserResponseDTO.class);
    }
}
