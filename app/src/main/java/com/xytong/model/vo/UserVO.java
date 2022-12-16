package com.xytong.model.vo;

import android.util.Log;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.xytong.model.dto.user.UserResponseDTO;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@ToString(callSuper = true)
@Data
public class UserVO implements Serializable {
    private String name = "未登录";
    private Integer id = -1;
    private String phoneNumber = "null";
    private String signature = "";
    private int gender = -1;
    private Long birthday = -1L;
    private String email = "null";
    private String userAvatarUrl = "";

    public UserVO() {
    }

    public static UserVO init(UserResponseDTO userResponseDTO) {
        if (userResponseDTO == null||userResponseDTO.getUsername() == null) {
            Log.i("init", "get user dto error");
            return null;
        }
        UserVO userVO = new UserVO();
        userVO.setName(userResponseDTO.getUsername());
        userVO.setBirthday(userResponseDTO.getBirthday_timestamp());
        userVO.setSexInteger(userResponseDTO.getGender());
        userVO.setEmail(userResponseDTO.getEmail());
        userVO.setUserAvatarUrl(userResponseDTO.getAvatar());
        userVO.setPhoneNumber(userResponseDTO.getPhone());
        userVO.setSignature(userResponseDTO.getSignature());
        return userVO;
    }

    @JsonIgnore
    public String getSexString() {
        String str = "未知";
        switch (gender) {
            case -1:
                str = "未知";
                break;
            case 0:
                str = "女";
                break;
            case 1:
                str = "男";
                break;
        }
        return str;
    }

    public String setSexInteger(String str) {
        switch (str) {
            case "unknown":
                gender = -1;
                break;
            case "female":
                gender = 0;
                break;
            case "male":
                gender = 1;
                break;
        }
        return str;
    }


}
