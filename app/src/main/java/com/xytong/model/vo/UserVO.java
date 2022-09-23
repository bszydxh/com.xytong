package com.xytong.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO implements Serializable {
    private String name = "未登录";
    private Integer id = -1;
    private String phoneNumber = "null";
    private String signature = "登录/注册";
    private int gender = -1;
    private Long birthday = -1L;
    private String email = "null";
    private String userAvatarUrl = "";
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

}
