package com.xytong.utils;

import android.content.Context;
import com.xytong.dao.SettingDao;
import com.xytong.model.dto.UserPostDTO;
import com.xytong.model.dto.UserResponseDTO;
import com.xytong.utils.poster.Poster;

public class UserDownloader {
    public static UserResponseDTO getUser(Context context, String username) {
        UserPostDTO userPostDTO = new UserPostDTO();
        userPostDTO.setTimestamp(String.valueOf(System.currentTimeMillis()));
        userPostDTO.setUsername(username);
        return Poster.jacksonPost(SettingDao.getUserUrl(context), userPostDTO, UserResponseDTO.class);
    }
}
